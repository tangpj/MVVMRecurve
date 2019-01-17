package com.tangpj.recurve.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tangpj.recurve.ui.creator.LoadingCreator
import com.tangpj.recurve.ui.creator.RecurveLoadingCreator
import com.tangpj.recurve.ui.strategy.LoadingStrategy

abstract class RecurveFragment()
    : Fragment(), LoadingCreator by RecurveLoadingCreator(){

    abstract fun onCreateBinding(inflater: LayoutInflater,
                                 container: ViewGroup?,
                                 savedInstanceState: Bundle?): ViewDataBinding?

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = onCreateBinding(inflater, container, savedInstanceState)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}