package com.tangpj.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.tangpj.recurve.resource.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

abstract class ItemKeyedBoundResource<Key, ResultType, RequestType> :
        ItemKeyedBound<Key, ResultType, RequestType>,
        RecurveBound<List<ResultType>, RequestType>,
        PageResult<ResultType>{

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    private val pageLoadState = MediatorLiveData<PageLoadState>()

    private val resultPagedList = MediatorLiveData<PagedList<ResultType>>()

    private fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    internal val itemKeyedDataSource
            = object : ItemKeyedDataSource<Key, ResultType>(){
        override fun loadInitial(params: LoadInitialParams<Key>, callback: LoadInitialCallback<ResultType>) {
            val obs = Observable.just(params)
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

                        changePageLoadState(PageLoadStatus.REFRESH, result, callback){
                            loadInitial(params, callback)

                        }
                    }, {e -> Timber.e(e)})

        }

        override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<ResultType>) {

            val obs = Observable.just(params)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({
                        val result = object : NetworkBoundResource<List<ResultType>, RequestType>(){

                            override fun createCall(): LiveData<ApiResponse<RequestType>> =
                                    this@ItemKeyedBoundResource.createAfterCall(params)

                            override fun saveCallResult(item: RequestType) =
                                    this@ItemKeyedBoundResource.saveCallResult(item)


                            override fun shouldFetch(data: List<ResultType>?): Boolean =
                                    this@ItemKeyedBoundResource.shouldFetch(data)


                            override fun loadFromDb(): LiveData<List<ResultType>> =
                                    this@ItemKeyedBoundResource.loadFromDb()

                        }.asLiveData()

                        changePageLoadState(PageLoadStatus.LOAD_AFTER, result, callback){
                            loadAfter(params, callback)
                        }
                    }, {e -> Timber.e(e)})
        }

        override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<ResultType>) {
            val obs = Observable.just(params)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({
                        val result = object : NetworkBoundResource<List<ResultType>, RequestType>(){

                            override fun createCall(): LiveData<ApiResponse<RequestType>> =
                                    this@ItemKeyedBoundResource.createBeforeCall(params)

                            override fun saveCallResult(item: RequestType) =
                                    this@ItemKeyedBoundResource.saveCallResult(item)


                            override fun shouldFetch(data: List<ResultType>?): Boolean =
                                    this@ItemKeyedBoundResource.shouldFetch(data)


                            override fun loadFromDb(): LiveData<List<ResultType>> =
                                    this@ItemKeyedBoundResource.loadFromDb()

                        }.asLiveData()

                        changePageLoadState(PageLoadStatus.LOAD_BEFORE, result, callback){
                            loadBefore(params, callback)
                        }
                    }, {e -> Timber.e(e)})
        }

        override fun getKey(item: ResultType): Key = this@ItemKeyedBoundResource.getKey(item)

    }

    private fun changePageLoadState(
            pageLoadStatus: PageLoadStatus,
            result: MediatorLiveData<Resource<List<ResultType>>>,
            callback: ItemKeyedDataSource.LoadCallback<ResultType>,
            retry: () -> Any){
        pageLoadState.addSource(result){
            when {
                it.networkState.status == Status.ERROR -> {
                    this.retry = retry
                }
                it.networkState.status == Status.SUCCESS -> {
                    it.data?.let { data -> callback.onResult(data) }
                }

            }
            pageLoadState.postValue(PageLoadState(pageLoadStatus, it.networkState))
        }
    }

    override fun asListing(config: PagedList.Config): Listing<ResultType> {
        val factory
                = RecurveItemSourceFactory(this)
        val pagedList = factory.toLiveData(config)

        return Listing(
                pagedList = pagedList,
                networkState = pageLoadState,
                retry = { retryAllFailed() },
                refresh = { itemKeyedDataSource.invalidate() })
    }


}

