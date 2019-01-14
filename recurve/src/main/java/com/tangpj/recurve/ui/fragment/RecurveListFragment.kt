package com.tangpj.recurve.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tangpj.recurve.databinding.FragmentRecurveListBinding

class RecurveListFragment<T> : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentRecurveListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initView(binding:  FragmentRecurveListBinding){

    }


}