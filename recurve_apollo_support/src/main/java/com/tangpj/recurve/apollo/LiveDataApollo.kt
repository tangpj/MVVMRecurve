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

import androidx.lifecycle.LiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloQueryWatcher
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.tangpj.recurve.resource.ApiResponse
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataApollo{
    private constructor()

    fun <R> from(watcher: ApolloQueryWatcher<R>): LiveData<ApiResponse<R>> =
            object : LiveData<ApiResponse<R>>() {
                var started = AtomicBoolean(false)

                override fun onActive() {
                    super.onActive()
                    if (started.compareAndSet(false, true)){
                        watcher.enqueueAndWatch(object : ApolloCall.Callback<R>() {

                            override fun onResponse(response: Response<R>) {
                                postValue(create(response = response))
                            }

                            override fun onFailure(e: ApolloException) {
                                postValue(ApiResponse.create(error = e))
                            }

                        })
                    }
                }
            }

}