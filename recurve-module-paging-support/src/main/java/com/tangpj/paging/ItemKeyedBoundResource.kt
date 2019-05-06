package com.tangpj.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.tangpj.recurve.resource.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.schedulers.ComputationScheduler
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class ItemKeyedBoundResource<Key, ResultType, RequestType> :
        ItemKeyedBound<Key, ResultType, RequestType>(),
        RecurveBound<List<ResultType>, RequestType>,
        PageResult<ResultType>{

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    private val compositeDisposable = CompositeDisposable()

    private val pageLoadState = MediatorLiveData<PageLoadState>()

    private val resultPagedList = MediatorLiveData<PagedList<ResultType>>()

    private fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        val retryDisposable = Observable.just(prevRetry).observeOn(Schedulers.io()).subscribe({
            it?.invoke()
        }, { e -> Timber.e(e) })
        prevRetry?.invoke()
        compositeDisposable.add(retryDisposable)
    }

    internal val itemKeyedDataSource
            = object : ItemKeyedDataSource<Key, ResultType>(){
        override fun loadInitial(params: LoadInitialParams<Key>, callback: LoadInitialCallback<ResultType>) {
            val realResult = MediatorLiveData<Resource<List<ResultType>>>()
            val disposable = Observable.just(params)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({
                        val result = object : NetworkBoundResource<List<ResultType>, RequestType>(){

                                override fun createCall(): LiveData<ApiResponse<RequestType>> =
                                        this@ItemKeyedBoundResource.createInitialCall(params)

                                override fun saveCallResult(item: RequestType) =
                                        this@ItemKeyedBoundResource.saveCallResult(item)


                                override fun shouldFetch(data: List<ResultType>?): Boolean =
                                        this@ItemKeyedBoundResource.shouldFetch(data)


                                override fun loadFromDb(): LiveData<List<ResultType>> =
                                        this@ItemKeyedBoundResource.loadFromDb()

                            }.asLiveData()
                        realResult.addSource(result){
                            realResult.value = it
                        }

                    }, { e -> Timber.e(e) })

            changePageLoadState(PageLoadStatus.REFRESH, realResult, callback){
                loadInitial(params, callback)

            }
            compositeDisposable.add(disposable)

        }

        override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<ResultType>) {
            val afterCall = createAfterCall(params)
            afterCall ?: return
            val realResult = MediatorLiveData<Resource<List<ResultType>>>()
            val disposable = Observable.just(params)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({
                        val result = object : NetworkBoundResource<List<ResultType>, RequestType>(){

                            override fun createCall(): LiveData<ApiResponse<RequestType>> =
                                    afterCall

                            override fun saveCallResult(item: RequestType) =
                                    this@ItemKeyedBoundResource.saveCallResult(item)


                            override fun shouldFetch(data: List<ResultType>?): Boolean =
                                    this@ItemKeyedBoundResource.shouldFetch(data)


                            override fun loadFromDb(): LiveData<List<ResultType>> =
                                    this@ItemKeyedBoundResource.loadFromDb()

                        }.asLiveData()
                        realResult.addSource(result){
                            realResult.value = it
                        }

                    }, { e -> Timber.e(e) })

            changePageLoadState(PageLoadStatus.LOAD_AFTER, realResult, callback){
                loadAfter(params, callback)
            }
            compositeDisposable.add(disposable)
        }

        override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<ResultType>) {
            val beforeCall = createBeforeCall(params)
            beforeCall ?: return
            val realResult = MediatorLiveData<Resource<List<ResultType>>>()
            val disposable = Observable.just(params)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({
                        val result = object : NetworkBoundResource<List<ResultType>, RequestType>(){

                            override fun createCall(): LiveData<ApiResponse<RequestType>> =
                                    beforeCall

                            override fun saveCallResult(item: RequestType) =
                                    this@ItemKeyedBoundResource.saveCallResult(item)


                            override fun shouldFetch(data: List<ResultType>?): Boolean =
                                    this@ItemKeyedBoundResource.shouldFetch(data)


                            override fun loadFromDb(): LiveData<List<ResultType>> =
                                    this@ItemKeyedBoundResource.loadFromDb()

                        }.asLiveData()
                        realResult.addSource(result){
                            realResult.value = it
                        }
                    }, { e -> Timber.e(e) })

            changePageLoadState(PageLoadStatus.LOAD_BEFORE, realResult, callback){
                loadBefore(params, callback)
            }

            compositeDisposable.add(disposable)
        }

        override fun getKey(item: ResultType): Key = this@ItemKeyedBoundResource.getKey(item)

    }

    private fun changePageLoadState(
            pageLoadStatus: PageLoadStatus,
            result: MediatorLiveData<Resource<List<ResultType>>>,
            callback: ItemKeyedDataSource.LoadCallback<ResultType>,
            retry: () -> Any){
        val changeDisposable = Observable.just(result)
                .observeOn(AndroidSchedulers.mainThread()).subscribe ({
                    pageLoadState.addSource(result){
                        when {
                            it.networkState.status == Status.ERROR -> {
                                this.retry = retry
                            }
                            it.networkState.status == Status.SUCCESS -> {
                                it.data?.let { data -> callback.onResult(data) }
                            }

                        }
                        pageLoadState.postValue(PageLoadState(pageLoadStatus, it.networkState))}

                }, { e -> Timber.e(e) } )
        compositeDisposable.add(changeDisposable)
    }

    override fun asListing(config: PagedList.Config): Listing<ResultType> {
        val factory
                = RecurveItemSourceFactory(this)
        val pagedList = factory.toLiveData(config)
        val resource = MediatorLiveData<Resource<PagedList<ResultType>>>()
        resource.addSource(pagedList){ _pagedList ->
            resource.value = Resource.success(_pagedList)
            resource.removeSource(pagedList)
        }
        resource.addSource(pageLoadState){
            when(it.networkState.status){
                Status.LOADING -> {
                    resource.value = Resource.loading(null)
                }
                Status.ERROR -> {
                    resource.value = Resource.error(it.networkState.msg ?: "unknown error")
                }
                Status.SUCCESS -> {
                    resource.removeSource(pageLoadState)

                }
            }
        }
        return Listing(
                resource = resource,
                pageLoadState = pageLoadState,
                retry = { retryAllFailed() },
                refresh = { itemKeyedDataSource.invalidate() })
    }


}

