package com.tangpj.paging.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import androidx.paging.ItemKeyedDataSource.*
import com.tangpj.paging.ItemKeyedBoundResource
import com.tangpj.paging.PageLoadState
import com.tangpj.paging.PageLoadStatus
import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.resource.NetworkBoundResource
import com.tangpj.recurve.resource.Resource
import com.tangpj.recurve.resource.Status
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class RecurveItemKeyedDataSource<Key, ResultType, RequestType> constructor(
        val itemKeyedBoundResource: ItemKeyedBoundResource<Key, ResultType, RequestType>) :
        ItemKeyedDataSource<Key, ResultType>(){

    internal val pageLoadState = MediatorLiveData<PageLoadState>()

    private val compositeDisposable = CompositeDisposable()

    private var retry: (() -> Any)? = null

    internal fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        val retryDisposable = if (prevRetry != null){
            Observable.just(prevRetry).observeOn(Schedulers.io()).subscribe({
                it?.invoke()
            }, { e -> Timber.e(e) })
        }else{
            null
        }

        prevRetry?.invoke()
        retryDisposable?.let {
            compositeDisposable.add(it)

        }
    }

    override fun loadInitial(params: LoadInitialParams<Key>, callback: LoadInitialCallback<ResultType>) {
        val realResult = MediatorLiveData<Resource<List<ResultType>>>()
        val disposable = Observable.just(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    val result = object : NetworkBoundResource<List<ResultType>, RequestType>(){

                        override fun createCall(): LiveData<ApiResponse<RequestType>> =
                                itemKeyedBoundResource.createInitialCall(params)

                        override fun saveCallResult(item: RequestType) =
                                itemKeyedBoundResource.saveCallResult(item)


                        override fun shouldFetch(data: List<ResultType>?): Boolean =
                                itemKeyedBoundResource.shouldFetch(data)


                        override fun loadFromDb(): LiveData<List<ResultType>> =
                                itemKeyedBoundResource.loadFromDb()

                    }.asLiveData()
                    realResult.addSource(result){
                        if (realResult.value != it){
                            realResult.value = it
                        }
                    }

                }, { e -> Timber.e(e) })

        changePageLoadState(PageLoadStatus.REFRESH, realResult, callback){
            loadInitial(params, callback)

        }
        compositeDisposable.add(disposable)

    }

    override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<ResultType>) {
        if(!itemKeyedBoundResource.hasNextPage()){
            return
        }
        val realResult = MediatorLiveData<Resource<List<ResultType>>>()
        val disposable = Observable.just(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    val result = object : NetworkBoundResource<List<ResultType>, RequestType>(){

                        override fun createCall(): LiveData<ApiResponse<RequestType>> =
                                itemKeyedBoundResource.createAfterCall(params) ?: MutableLiveData()

                        override fun saveCallResult(item: RequestType) {
                            return itemKeyedBoundResource.saveCallResult(item)
                        }

                        override fun shouldFetch(data: List<ResultType>?): Boolean =
                                itemKeyedBoundResource.shouldFetch(data)


                        override fun loadFromDb(): LiveData<List<ResultType>> =
                                itemKeyedBoundResource.loadFromDb()

                    }.asLiveData()
                    realResult.addSource(result){
                        if (realResult.value != it){
                            realResult.value = it
                        }
                    }

                }, { e -> Timber.e(e) })

        changePageLoadState(PageLoadStatus.LOAD_AFTER, realResult, callback){
            loadAfter(params, callback)
        }
        compositeDisposable.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<ResultType>) {
        if(!itemKeyedBoundResource.hasPreviousPage()){
            return
        }
        val beforeCall = itemKeyedBoundResource.createBeforeCall(params)
        beforeCall ?: return
        val realResult = MediatorLiveData<Resource<List<ResultType>>>()
        val disposable = Observable.just(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    val result = object : NetworkBoundResource<List<ResultType>, RequestType>(){

                        override fun createCall(): LiveData<ApiResponse<RequestType>> =
                                beforeCall

                        override fun saveCallResult(item: RequestType) =
                                itemKeyedBoundResource.saveCallResult(item)


                        override fun shouldFetch(data: List<ResultType>?): Boolean =
                                itemKeyedBoundResource.shouldFetch(data)


                        override fun loadFromDb(): LiveData<List<ResultType>> =
                                itemKeyedBoundResource.loadFromDb()

                    }.asLiveData()
                    realResult.addSource(result){
                        if (realResult.value != it){
                            realResult.value = it
                        }
                    }
                }, { e -> Timber.e(e) })

        changePageLoadState(PageLoadStatus.LOAD_BEFORE, realResult, callback){
            loadBefore(params, callback)
        }

        compositeDisposable.add(disposable)
    }

    override fun getKey(item: ResultType): Key = itemKeyedBoundResource.getKey(item)

    private fun changePageLoadState(
            pageLoadStatus: PageLoadStatus,
            result: MediatorLiveData<Resource<List<ResultType>>>,
            callback: LoadCallback<ResultType>,
            retry: () -> Any){
        val changeDisposable = Observable.just(result)
                .observeOn(AndroidSchedulers.mainThread()).subscribe ({
                    pageLoadState.addSource(result){
                        when {
                            it.networkState.status == Status.ERROR -> {
                                pageLoadState.removeSource(result)
                                this.retry = retry
                            }
                            it.networkState.status == Status.SUCCESS -> {
                                pageLoadState.removeSource(result)
                                try {
                                    it.data?.let {
                                        data -> callback.onResult(data)
                                    }
                                }catch (e: Exception){
                                    Timber.e(e)
                                }

                            }

                        }
                        pageLoadState.postValue(PageLoadState(pageLoadStatus, it.networkState))}

                }, { e -> Timber.e(e) } )
        compositeDisposable.add(changeDisposable)
    }


}