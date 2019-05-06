package com.tangpj.paging

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.tangpj.recurve.resource.*

abstract class ItemKeyedBoundResource<Key, ResultType, RequestType> :
        ItemKeyedBound<Key, ResultType, RequestType>(),
        RecurveBound<List<ResultType>, RequestType>,
        PageResult<ResultType>{

    private val resultPagedList = MediatorLiveData<PagedList<ResultType>>()

    override fun asListing(config: PagedList.Config): Listing<ResultType> {
        val factory
                = RecurveItemSourceFactory(this)
        val pagedList = factory.toLiveData(config)

        val resource = MediatorLiveData<Resource<PagedList<ResultType>>>()

        val pageLoadState = Transformations.switchMap(factory.sourceLiveData) {
            it.pageLoadState
        }

        resource.addSource(pagedList){ _pagedList ->
            val loadState = factory.sourceLiveData.value?.pageLoadState
            loadState?: return@addSource
            resource.addSource(loadState){
                when(it.networkState.status){
                    Status.LOADING -> {
                        resource.value = Resource.loading(null)
                    }
                    Status.ERROR -> {
                        resource.value = Resource.error(it.networkState.msg ?: "unknown error")
                    }
                    Status.SUCCESS -> {
                        resource.value = Resource.success(_pagedList)
                        resource.removeSource(pagedList)
                        resource.removeSource(pageLoadState)

                    }
                }
            }
        }

        return Listing(
                resource = resource,
                pageLoadState = pageLoadState,
                retry = { factory.sourceLiveData.value?.retryAllFailed() },
                refresh = { factory.sourceLiveData.value?.invalidate() })
    }

}

