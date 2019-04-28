package com.tangpj.paging

import androidx.lifecycle.LiveData
import com.tangpj.recurve.resource.NetworkBoundResource

internal abstract class RecourveBoundResourceProxy<ResultType, RequestType> constructor(
        private val recurveBound: RecurveBound<List<ResultType>, RequestType>) :
        NetworkBoundResource<List<ResultType>, RequestType>(){

    override fun saveCallResult(item: RequestType) =
            recurveBound.saveCallResult(item)


    override fun shouldFetch(data: List<ResultType>?): Boolean =
            recurveBound.shouldFetch(data)


    override fun loadFromDb(): LiveData<List<ResultType>> =
            recurveBound.loadFromDb()

}