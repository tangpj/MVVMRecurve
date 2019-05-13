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

package com.tangpj.paging

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.tangpj.recurve.resource.Resource

/**
 * Data class that is necessary for a UI to show a listing and interact w/ the rest of the system
 */
data class Listing<T>(
        // the LiveData of paged lists for the UI to observe
        val pagedList: LiveData<PagedList<T>>,
        // represents the network request status to show to the user
        val pageLoadState: LiveData<PageLoadState>,
        // refreshes the whole data and fetches it from scratch.
        val refresh: () -> Unit,
        // retries any failed requests.
        val retry: () -> Unit)

interface PageResult<T>{
    fun asListing(config: PagedList.Config): Listing<T>
}