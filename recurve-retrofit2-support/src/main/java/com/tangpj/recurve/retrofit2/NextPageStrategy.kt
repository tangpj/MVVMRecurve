package com.tangpj.recurve.retrofit2

interface NextPageStrategy{
    fun nextPageRule(links: Map<String, String>): Int
}