package com.recurve.paging

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

interface RecurveBound<ResultType, RequestType>{
    @WorkerThread
    fun saveCallResult(item: RequestType)

    @MainThread
    fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    fun loadFromDb(): LiveData<ResultType>

    @MainThread
    fun hasNextPage() = false

    @MainThread
    fun hasPreviousPage() = false
}