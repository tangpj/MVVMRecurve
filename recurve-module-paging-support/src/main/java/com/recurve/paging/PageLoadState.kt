package com.recurve.paging

import com.recurve.core.resource.NetworkState


enum class PageLoadStatus {
    REFRESH,
    LOAD_AFTER,
    LOAD_BEFORE
}

class PageLoadState(val status: PageLoadStatus, val networkState: NetworkState)