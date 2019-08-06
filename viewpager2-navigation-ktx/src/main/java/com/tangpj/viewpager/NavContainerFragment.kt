package com.tangpj.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class NavContainerFragment private constructor(): Fragment(){

    private var graphId = 0
    private var navController: NavController? = null

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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        graphId = arguments?.getInt(KEY_NAV_GRAPH_ID, 0) ?: 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_nav_container, container, false)
        val fragment = NavHostFragment.create(graphId)
        childFragmentManager.beginTransaction().add(R.id.nav_container, fragment)
                .setPrimaryNavigationFragment(fragment).commitNow()
        navController = fragment.navController
        return view
    }


    fun getNavController() : NavController? = navController
}