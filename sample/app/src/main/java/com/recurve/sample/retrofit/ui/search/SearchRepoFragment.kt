package com.recurve.sample.retrofit.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.recurve.core.ui.fragment.RecurveFragment
import com.recurve.sample.R
import com.recurve.sample.databinding.FragmentSearchRepoBinding

class SearchRepoFragment : RecurveFragment(){

    private lateinit var binding: FragmentSearchRepoBinding

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_search_repo, container, false)
        return binding
    }



}