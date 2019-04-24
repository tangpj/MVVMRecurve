package com.tangpj.paging

import androidx.lifecycle.LiveData
import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.resource.NetworkBoundResource

class PagedBoundResource<ResultType, RequestType>: NetworkBoundResource<ResultType, RequestType>(){
    override fun saveCallResult(item: RequestType) {

    }

    override fun shouldFetch(data: ResultType?): Boolean {
    }

    override fun loadFromDb(): LiveData<ResultType> {
    }

    override fun createCall(): LiveData<ApiResponse<RequestType>> {
    }

}