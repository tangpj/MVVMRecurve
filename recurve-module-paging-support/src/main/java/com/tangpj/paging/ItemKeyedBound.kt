package com.tangpj.paging

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import com.tangpj.recurve.resource.ApiResponse

interface ItemKeyedBound<Key, ResultType, RequestType> : RecurveBound<ResultType, RequestType>{

    @MainThread
    fun createInitialCall(params: ItemKeyedDataSource.LoadInitialParams<Key>): LiveData<ApiResponse<RequestType>>

    @MainThread
    fun createAfterCall(params: ItemKeyedDataSource.LoadParams<Key>): LiveData<ApiResponse<RequestType>>

    @MainThread
    fun createBeforeCall(params: ItemKeyedDataSource.LoadParams<Key>): LiveData<ApiResponse<RequestType>>

    fun getKey(item: ResultType): Key
}