package com.tangpj.paging

import androidx.lifecycle.LiveData
import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.resource.NetworkBoundResource

class ItemKeyedNetworkBound<ResultType, RequestType> :
        NetworkBoundResource<ResultType,RequestType>(){

    private ItemK

    override fun saveCallResult(item: RequestType) {

    }

    override fun shouldFetch(data: ResultType?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadFromDb(): LiveData<ResultType> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createCall(): LiveData<ApiResponse<RequestType>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}