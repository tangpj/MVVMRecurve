package com.tangpj.recurve.resource

interface NextPageStrategy{
    fun nextPageRule(links: Map<String, String>): Int
}