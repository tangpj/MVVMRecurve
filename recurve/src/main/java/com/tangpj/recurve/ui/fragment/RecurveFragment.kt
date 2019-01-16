package com.tangpj.recurve.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tangpj.recurve.ui.strategy.LoadingStrategy

abstract class RecurveFragment : Fragment(){

    private var mLoadingStrategy: LoadingStrategy? = null

    abstract fun onCreateBinding(inflater: LayoutInflater,
                                 container: ViewGroup?,
                                 savedInstanceState: Bundle?): ViewDataBinding?

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = onCreateBinding(inflater, container, savedInstanceState)
        binding?.let {
            mLoadingStrategy = loadingStrategy()
        }
        mLoadingStrategy?.let {
            
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun loadingStrategy(): LoadingStrategy? = null

}