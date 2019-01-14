package com.tangpj.recurve.ui.strategy

interface LoadingStrategy{

    fun onLoading(isLoading: Boolean)

    fun calcel()

}