/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tangpj.recurve.retrofit2

import androidx.lifecycle.LiveData
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.MediatorLiveData
import com.tangpj.recurve.resource.AbstractNetWorkBoundResource
import com.tangpj.recurve.resource.Resource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(): AbstractNetWorkBoundResource<ResultType, RequestType>() {

    override fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {

                    Observable.fromArray(response)
                            .subscribeOn(Schedulers.io())
                            .map { saveCallResult(processResponse(it)) }
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                result.addSource(loadFromDb()) { newData
                                    -> setValue(Resource.success(newData))
                                }
                            }
                }
                is ApiEmptyResponse -> {

                    Observable.fromArray(response)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                result.addSource(loadFromDb()) { newData
                                    -> setValue(Resource.success(newData))
                                }
                            }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }
        }
    }

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}
