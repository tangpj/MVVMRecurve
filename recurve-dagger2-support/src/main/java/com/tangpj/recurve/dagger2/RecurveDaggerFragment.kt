package com.tangpj.recurve.dagger2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.tangpj.recurve.ui.creator.RecurveLoadingCreator
import com.tangpj.recurve.ui.strategy.LoadingStrategy
import dagger.android.support.DaggerFragment

abstract class RecurveDaggerFragment: DaggerFragment(){

    private val loadingCreator by lazy { RecurveLoadingCreator() }

    abstract fun onCreateBinding(inflater: LayoutInflater,
                                 container: ViewGroup?,
                                 savedInstanceState: Bundle?): ViewDataBinding?

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = onCreateBinding(inflater, container, savedInstanceState)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun loadingStrategy(key: String): LoadingStrategy? = loadingCreator.getLoadingStrategy(key)
}