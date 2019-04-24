package com.tangpj.paging

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.paging.ItemKeyedDataSource
import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.resource.NetworkBoundResource

abstract class ItemKeyedBoundResource<Key, ResultType, RequestType>:  ItemKeyed<Key, ResultType, RequestType>{

    private val itemKeyedDataSource
            = object : ItemKeyedDataSource<Key, ResultType>(){
        override fun loadInitial(params: LoadInitialParams<Key>, callback: LoadInitialCallback<ResultType>) {
            val call = object : NetworkBoundResource<ResultType, RequestType>(){
                override fun saveCallResult(item: RequestType) {

                }

                override fun shouldFetch(data: ResultType?): Boolean =
                    this@ItemKeyedBoundResource.shouldFetch(data)


                override fun loadFromDb(): LiveData<ResultType> = {
                    this@ItemKeyedBoundResource.
                }

                override fun createCall(): LiveData<ApiResponse<RequestType>> =
                    this@ItemKeyedBoundResource.createInitialCall(params)


            }.asLiveData()
        }

        override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<ResultType>) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<ResultType>) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getKey(item: ResultType): Key = this@ItemKeyedBoundResource.getKey(item)

    }

    @MainThread
    override fun createAfterCall(): LiveData<ApiResponse<RequestType>> {

    }

}

