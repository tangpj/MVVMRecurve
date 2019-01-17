package com.tangpj.recurve.ui.creator

import com.tangpj.recurve.ui.strategy.LoadingStrategy

interface LoadingCreator{

    fun addLoadingStrategy(pair: Pair<String, LoadingStrategy>)

    fun getLoadingStrategy(key: String): LoadingStrategy?

    fun clear()
}