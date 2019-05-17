package com.tangpj.paging

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.paging.ItemKeyedDataSource
import com.tangpj.recurve.resource.ApiResponse

abstract class ItemKeyedBound<Key, ResultType, RequestType>{

    @MainThread
    abstract fun createInitialCall(params: ItemKeyedDataSource.LoadInitialParams<Key>): LiveData<ApiResponse<RequestType>>

    abstract fun getKey(item: ResultType): Key

    @MainThread
    open fun createAfterCall(
            params: ItemKeyedDataSource.LoadParams<Key>): LiveData<ApiResponse<RequestType>>? = null

    @MainThread
    open fun createBeforeCall(
            params: ItemKeyedDataSource.LoadParams<Key>): LiveData<ApiResponse<RequestType>>? = null

}