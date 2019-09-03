package com.tangpj.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment

class NavContainerFragment : Fragment() {

    var navHostFragment = MutableLiveData<NavHostFragment>()

    private var fragmentCreatorPair : Pair<Int, (ViewDataBinding) -> Unit>? = null

    companion object {
        internal const val KEY_NAV_GRAPH_ID =
                "com.tangpj.viewpager.NavContainerFragment.graphId"
        fun create(@IdRes navGraphId: Int) =
                NavContainerFragment().apply {
                    val bundle = Bundle()
                    bundle.putInt(KEY_NAV_GRAPH_ID, navGraphId)
                    arguments = bundle
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val graphId = arguments?.getInt(KEY_NAV_GRAPH_ID, 0) ?: 0
        val layoutId = fragmentCreatorPair?.first ?: R.layout.fragment_nav_container
        val action = fragmentCreatorPair?.second
        val binding =
                DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, container, false)
        action?.invoke(binding)
        val fragment = NavHostFragment.create(graphId)
        childFragmentManager.beginTransaction().add(R.id.nav_host_container, fragment)
                .setPrimaryNavigationFragment(fragment).commitNow()
        navHostFragment.value = fragment
        return binding.root
    }

    fun initContainer(value : Pair<Int, (ViewDataBinding) -> Unit>?){
        fragmentCreatorPair = value
    }


}
