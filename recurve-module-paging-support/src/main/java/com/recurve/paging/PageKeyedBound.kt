package com.recurve.paging

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.paging.PageKeyedDataSource
import com.recurve.core.resource.ApiResponse


interface PageKeyedBound<Key,ResultType, RequestType> {

    @MainThread
    fun createInitialCall(params: PageKeyedDataSource.LoadInitialParams<Key>): LiveData<ApiResponse<RequestType>>

    @MainThread
    fun createAfterCall(params: PageKeyedDataSource.LoadParams<Key>): LiveData<ApiResponse<RequestType>>

    @MainThread
    fun createBeforeCall(params: PageKeyedDataSource.LoadParams<Key>): LiveData<ApiResponse<RequestType>>

}