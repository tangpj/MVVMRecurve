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
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import com.tangpj.recurve.resource.ApiResponse
import com.tangpj.recurve.resource.NextPageStrategy

/**
 * Created by tang on 2018/2/28.
 * A Retrofit adapter that converts the Call into a LiveData of ApiResponse.
 * @param <R>
</R> */
class LiveDataCallAdapter<R> @JvmOverloads constructor(private val responseType: Type
                                                       , val nextPageStrategy: NextPageStrategy<Response<*>>? = null)
    : CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> =
            object : LiveData<ApiResponse<R>>() {
                var started = AtomicBoolean(false)
                override fun onActive() {
                    super.onActive()
                    if (started.compareAndSet(false, true)) {
                        call.enqueue(object : Callback<R> {
                            override fun onResponse(call: Call<R>, response: Response<R>) {
                                nextPageStrategy?.setResponse(response)
                                postValue(create(response = response))
                            }
                            override fun onFailure(call: Call<R>, throwable: Throwable) {
                                postValue(ApiResponse.create(throwable))
                            }
                        })
                    }
                }
            }

}
