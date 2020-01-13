package com.recurve.sample.retrofit.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.recurve.core.ui.fragment.RecurveFragment
import com.recurve.sample.R
import com.recurve.sample.databinding.FragmentSearchRepoBinding

class SearchRepoFragment : RecurveFragment(){

    private lateinit var binding: FragmentSearchRepoBinding
    private lateinit var searchViewModel: ViewModel

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_search_repo, container, false)

        return binding
    }

    private fun initView(){
    }
}