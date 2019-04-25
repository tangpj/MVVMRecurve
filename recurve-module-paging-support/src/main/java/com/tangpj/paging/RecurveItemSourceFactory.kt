package com.tangpj.paging

import androidx.paging.DataSource


class RecurveItemSourceFactory<Key, ResultType, RequestType>(
        private val itemKeyedBoundResource: ItemKeyedBoundResource<Key, ResultType, RequestType>) : DataSource.Factory< Key,ResultType>(){

    override fun create()
            = itemKeyedBoundResource.itemKeyedDataSource

}