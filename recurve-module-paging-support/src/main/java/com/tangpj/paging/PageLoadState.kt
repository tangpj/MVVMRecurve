package com.tangpj.paging

import com.tangpj.recurve.resource.NetworkState

enum class PageLoadStatus {
    REFRESH,
    LOAD_AFTER,
    LOAD_BEFORE
}

class PageLoadState(val status: PageLoadStatus, val networkState: NetworkState)