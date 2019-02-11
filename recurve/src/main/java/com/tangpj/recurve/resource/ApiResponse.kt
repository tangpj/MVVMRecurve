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
package com.tangpj.recurve.resource

import java.util.regex.Pattern

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {

    companion object {
        @JvmStatic
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }
    }
}

/**
 * separate class for HTTP 204 resposes so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(
        val body: T) : ApiResponse<T>() {

    private var mNextPageStrategy: NextPageStrategy? = null

    constructor(body: T, nextPageStrategy: NextPageStrategy? = null)
            : this(body = body){
        mNextPageStrategy= nextPageStrategy
    }


    val nextPage: Int by lazy(LazyThreadSafetyMode.NONE) {
        mNextPageStrategy?.nextPageRule() ?: -1
    }

}

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()