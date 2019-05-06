package com.tangpj.paging

import androidx.lifecycle.MediatorLiveData
import androidx.paging.DataSource
import com.tangpj.paging.source.RecurveItemKeyedDataSource


class RecurveItemSourceFactory<Key, ResultType, RequestType>(
        private val itemKeyedBoundResource: ItemKeyedBoundResource<Key, ResultType, RequestType>) : DataSource.Factory< Key,ResultType>(){

    internal val sourceLiveData = MediatorLiveData<RecurveItemKeyedDataSource<Key, ResultType, RequestType>>()

    override fun create() : DataSource<Key, ResultType>{
        val source = RecurveItemKeyedDataSource(itemKeyedBoundResource)
        sourceLiveData.postValue(source)
        return source
    }


}