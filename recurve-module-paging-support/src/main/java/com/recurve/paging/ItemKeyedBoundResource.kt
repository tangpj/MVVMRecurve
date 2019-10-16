package com.recurve.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData

abstract class ItemKeyedBoundResource<Key, ResultType, RequestType> :
        ItemKeyedBound<Key, ResultType, RequestType>(),
        RecurveBound<List<ResultType>, RequestType>,
        PageResult<ResultType>{

    private val resultPagedList = MediatorLiveData<PagedList<ResultType>>()

    override fun asListing(config: PagedList.Config): LiveData<Listing<ResultType>> {
        val factory
                = RecurveItemSourceFactory(this)
        val pagedList = factory.toLiveData(config)

        val pageLoadState = Transformations.switchMap(factory.sourceLiveData) {
            it.pageLoadState
        }
        val result = MediatorLiveData<Listing<ResultType>>()
        result.value = Listing(
                pagedList = pagedList,
                pageLoadState = pageLoadState,
                retry = { factory.sourceLiveData.value?.retryAllFailed() },
                refresh = { factory.sourceLiveData.value?.invalidate() })
        return result
    }

}

