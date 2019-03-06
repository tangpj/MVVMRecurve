/*
 * Copyright 2018, The TangPJ
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

package com.tangpj.recurve.apollo

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloPrefetch
import com.apollographql.apollo.ApolloQueryWatcher
import com.apollographql.apollo.ApolloSubscriptionCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.cache.normalized.ApolloStoreOperation
import com.apollographql.apollo.exception.ApolloException
import com.tangpj.recurve.resource.ApiEmptyResponse
import com.tangpj.recurve.resource.ApiResponse


@Suppress("unused")
object LiveDataApollo {
    /**
     * Converts an [ApolloQueryWatcher] to a LiveData.
     *
     * @param watcher the ApolloQueryWatcher to convert.
     * @param T       the value type.
     * @return the converted LiveData.
     * @throws NullPointerException if watcher == null
     */
    fun <T> from(@NonNull watcher: ApolloQueryWatcher<T>): LiveData<ApiResponse<T>> {
        checkNotNull(watcher){ 
            "watcher == null"
        }
        return object : LiveData<ApiResponse<T>>() {
            override fun onActive() {
                super.onActive()
                watcher.enqueueAndWatch(object : ApolloCall.Callback<T>() {
                    override fun onResponse(response: Response<T>) {
                        postValue(create(response))
                    }

                    override fun onFailure(e: ApolloException) {
                        postValue(ApiResponse.create(e))
                    }
                })
            }
        }
    }

    /**
     * Converts an [ApolloCall] to a [LiveData]. The number of emissions this LiveData will have is based
     * on the [com.apollographql.apollo.fetcher.ResponseFetcher] used with the call.
     *
     * @param call the ApolloCall to convert.
     * @param T    the value type.
     * @return the converted LiveData.
     * @throws NullPointerException if originalCall == null
     */
    fun <T> from(@NonNull call: ApolloCall<T>): LiveData<ApiResponse<T>> {
        checkNotNull(call){
            "call == null"
        }
        return object : LiveData<ApiResponse<T>>() {
            override fun onActive() {
                super.onActive()
                call.enqueue(object : ApolloCall.Callback<T>() {
                    override fun onResponse(response: Response<T>) {
                        postValue(create(response))
                    }

                    override fun onFailure(e: ApolloException) {
                        postValue(ApiResponse.create(e))
                    }

                    override fun onStatusEvent(event: ApolloCall.StatusEvent) {
                        if (event == ApolloCall.StatusEvent.COMPLETED) {
                        }
                    }
                })
            }
        }
    }

    /**
     * Converts an [ApolloPrefetch] to a LiveData.
     *
     * @param prefetch the ApolloPrefetch to convert.
     * @return the converted LiveData.
     * @throws NullPointerException if prefetch == null
     */
    fun <T> from(@NonNull prefetch: ApolloPrefetch): LiveData<ApiResponse<T>> {
        checkNotNull(prefetch){
            "prefetch == null"
        }
        return object : LiveData<ApiResponse<T>>() {
            var started = false

            @MainThread
            override fun setValue(value: ApiResponse<T>?) {
                started = true
                super.setValue(value)
            }

            override fun postValue(value: ApiResponse<T>?) {
                started = true
                super.postValue(value)
            }

            override fun onActive() {
                super.onActive()
                if (!started) {
                    started = true
                    prefetch.enqueue(object : ApolloPrefetch.Callback() {
                        override fun onSuccess() {
                            postValue(ApiEmptyResponse())
                        }

                        override fun onFailure(e: ApolloException) {
                            postValue(ApiResponse.create(e))
                        }
                    })
                }
            }
        }
    }

    /**
     * Converts an [ApolloSubscriptionCall] to a LiveData.
     *
     * @param call the ApolloPrefetch to convert.
     * @return the converted LiveData.
     * @throws NullPointerException if prefetch == null
     */
    fun <T> from(@NonNull call: ApolloSubscriptionCall<T>): LiveData<ApiResponse<T>> {
        checkNotNull(call){
            "call == null"
        }
        return object : LiveData<ApiResponse<T>>() {
            override fun onActive() {
                super.onActive()
                call.execute(object : ApolloSubscriptionCall.Callback<T> {
                    override fun onTerminated() {

                    }

                    override fun onResponse(response: Response<T>) {
                        postValue(create(response))
                    }

                    override fun onFailure(e: ApolloException) {
                        postValue(ApiResponse.create(e))
                    }

                    override fun onCompleted() {
                        postValue(ApiEmptyResponse())
                    }
                })
            }
        }
    }

    /**
     * Converts an [ApolloStoreOperation] to a LiveData.
     *
     * @param operation the ApolloStoreOperation to convert.
     * @param T         the value type.
     * @return the converted LiveData.
     */
    fun <T> from(@NonNull operation: ApolloStoreOperation<T>): LiveData<T?> {
        checkNotNull(operation){
            "operation == null"
        }
        return object : LiveData<T?>() {
            override fun onActive() {
                super.onActive()
                operation.enqueue(object : ApolloStoreOperation.Callback<T> {
                    override fun onSuccess(result: T) {
                        postValue(result)
                    }

                    override fun onFailure(t: Throwable?) {
                        postValue(null)
                    }
                })
            }
        }
    }
}



