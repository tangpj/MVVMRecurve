package com.tangpj.viewpager

import android.content.Intent
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.setupWithNavController(
        navGraphIds :List<Int>,
        fragmentManager: FragmentManager,
        intent: Intent) : LiveData<NavController>{

    val graphIdToTagMap = SparseArray<String>()

    val selectedNavController = MutableLiveData<NavController>()

    var firstFragmentGraphId = 0

    navGraphIds.forEachIndexed{ index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        val navHostFragment = obtainNavHostFragment(
                fragmentTag,
                navGraphId
        )

    }

}

private fun ViewPager2.setupItemReselected(
        graphIdToTagMap: SparseArray<String>,
        fragmentManager: FragmentManager) {
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            val newlySelectedItemTag = graphIdToTagMap[position]
            val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                    as NavHostFragment
            val navController = selectedFragment.navController
            // Pop the back stack to the start destination of the current navController graph
            navController.popBackStack(
                    navController.graph.startDestination, false
            )
        }
    })
}

private fun detachNavHostFragment(
        fragmentManager: FragmentManager,
        navHostFragment: NavHostFragment) {
    fragmentManager.beginTransaction()
            .detach(navHostFragment)
            .commitNow()
}

private fun attachNavHostFragment(
        fragmentManager: FragmentManager,
        navHostFragment: NavHostFragment,
        isPrimaryNavFragment: Boolean) {
    fragmentManager.beginTransaction()
            .attach(navHostFragment)
            .apply {
                if (isPrimaryNavFragment) {
                    setPrimaryNavigationFragment(navHostFragment)
                }
            }
            .commitNow()

}

private fun obtainNavHostFragment(
        fragmentManager: FragmentManager,
        fragmentTag: String,
        navGraphId: Int
): NavHostFragment {
    // If the Nav Host fragment exists, return it
    val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
    existingFragment?.let { return it }

    // Otherwise, create it and return it.
    val navHostFragment = NavHostFragment.create(navGraphId)
    return navHostFragment
}

private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {
    val backStackCount = backStackEntryCount
    for (index in 0 until backStackCount) {
        if (getBackStackEntryAt(index).name == backStackName) {
            return true
        }
    }
    return false
}

private fun getFragmentTag(index: Int) = "ViewPager2#$index"


private class NavHostPagerAdapter(
        val activity: FragmentActivity,
        val navGraphIds: List<Int>
) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int = navGraphIds.size

    override fun createFragment(position: Int): Fragment{
        val fragment = obtainNavHostFragment(activity.supportFragmentManager, getFragmentTag(position), navGraphIds[position])


    }

}
