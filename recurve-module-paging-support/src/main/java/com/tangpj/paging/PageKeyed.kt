package com.tangpj.paging

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.ItemKeyedDataSource
import com.tangpj.recurve.resource.ApiResponse


interface PageKeyed<Key,ResultType, RequestType> {

    @MainThread
    fun createInitialCall(params: ItemKeyedDataSource.LoadInitialParams<Key>): LiveData<ApiResponse<RequestType>>

    @MainThread
    fun createAfterCall(): LiveData<ApiResponse<RequestType>>

    @MainThread
    fun createBeforeCall(): LiveData<ApiResponse<RequestType>>

    @WorkerThread
    fun saveCallResult(item: RequestType)

    @MainThread
    fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    fun loadFromDb(): LiveData<ResultType>
}