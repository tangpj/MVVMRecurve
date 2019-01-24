package com.tangpj.recurve.dagger2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tangpj.recurve.databinding.FragmentRecurveListBinding
import com.tangpj.recurve.ui.creator.LoadingCreator
import com.tangpj.recurve.ui.creator.RecurveLoadingCreator
import com.tangpj.recurve.ui.creator.RecyclerViewCreator
import com.tangpj.recurve.recyclerview.creator.Creator
import com.tangpj.recurve.recyclerview.adapter.ModulesAdapter


open class RecurveDaggerListFragment
    : Fragment(), LoadingCreator by RecurveLoadingCreator(), RecyclerViewCreator {

    private val mAdapter = ModulesAdapter()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentRecurveListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initView(binding: FragmentRecurveListBinding){
        binding.rvL.adapter = mAdapter
    }

    override fun addItemCreator(creator: Creator) {
        mAdapter.addCreator(creator)
    }

    override fun addItemCreator(index: Int, creator: Creator) {
        mAdapter.addCreator(index, creator)
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(context)


}