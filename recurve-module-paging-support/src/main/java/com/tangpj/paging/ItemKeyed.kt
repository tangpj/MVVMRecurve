package com.tangpj.paging

interface ItemKeyed<Key, ResultType, RequestType> : PageKeyed<Key, ResultType, RequestType>{

    fun getKey(item: ResultType): Key
}