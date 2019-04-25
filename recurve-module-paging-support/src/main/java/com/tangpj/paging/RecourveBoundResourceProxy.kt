package com.tangpj.paging

import androidx.lifecycle.LiveData
import com.tangpj.recurve.resource.NetworkBoundResource

internal abstract class RecourveBoundResourceProxy<ResultType, RequestType>(
        private val recurveBound: RecurveBound<ResultType, RequestType>) :
        NetworkBoundResource<ResultType, RequestType>(){

    override fun saveCallResult(item: RequestType) =
            recurveBound.saveCallResult(item)


    override fun shouldFetch(data: ResultType?): Boolean =
            recurveBound.shouldFetch(data)


    override fun loadFromDb(): LiveData<ResultType> =
            loadFromDb()

}