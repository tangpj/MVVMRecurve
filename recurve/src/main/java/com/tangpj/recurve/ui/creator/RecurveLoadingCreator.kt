package com.tangpj.recurve.ui.creator

import com.tangpj.recurve.ui.strategy.LoadingStrategy

class RecurveLoadingCreator: LoadingCreator{

    private val loadingMap: MutableMap<String, LoadingStrategy> = mutableMapOf()

    override fun addLoadingStrategy(pair: Pair<String, LoadingStrategy>) {
        loadingMap[pair.first] = pair.second
    }

    override fun getLoadingStrategy(key: String): LoadingStrategy? = loadingMap[key]
}