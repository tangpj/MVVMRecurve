package com.tangpj.paging

abstract class PagedKeyedBoundResource<Key, ResultType, RequestType>:
        PageKeyedBound<Key,ResultType, RequestType>, PageResult<ResultType>{


}