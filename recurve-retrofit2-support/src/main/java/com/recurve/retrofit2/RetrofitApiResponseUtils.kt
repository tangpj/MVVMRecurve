/*
 * Copyright (C) 2018 Tang
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
package com.recurve.retrofit2

import com.recurve.core.resource.ApiEmptyResponse
import com.recurve.core.resource.ApiErrorResponse
import com.recurve.core.resource.ApiResponse
import com.recurve.core.resource.ApiSuccessResponse
import retrofit2.Response

fun <T> create(response: Response<T>): ApiResponse<T> {
    return if (response.isSuccessful) {
        val body = response.body()
        if (body == null || response.code() == 204) {
            ApiEmptyResponse()
        } else {
            ApiSuccessResponse(body = body)
        }
    } else {
        val msg = response.errorBody()?.string()
        val errorMsg = if (msg.isNullOrEmpty()) {
            response.message()
        } else {
            msg
        }
        ApiErrorResponse(errorMsg ?: "unknown error")
    }
}