package com.tangpj.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class NavContainerFragment private constructor(): Fragment(){

    val navController = MutableLiveData<NavController>()

    companion object{
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
        val view = inflater.inflate(R.layout.fragment_nav_container, container, false)
        val fragment = NavHostFragment.create(graphId)
        childFragmentManager.beginTransaction().add(R.id.nav_container, fragment)
                .setPrimaryNavigationFragment(fragment).commitNow()
        navController.value = fragment.navController
        return view
    }

}