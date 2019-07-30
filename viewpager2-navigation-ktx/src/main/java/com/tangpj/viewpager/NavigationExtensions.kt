package com.tangpj.viewpager

import android.content.Intent
import android.util.SparseArray
import android.util.SparseIntArray
import android.util.SparseLongArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.setupWithNavController(
        activity: FragmentActivity,
        navGraphIds :List<Int>,
        intent: Intent) : LiveData<NavController>{

    val selectedNavController = MutableLiveData<NavController>()

    adapter = NavHostPagerAdapter(activity, navGraphIds)

    return selectedNavController
}

private fun ViewPager2.setupDeepLinks(
        navGraphIds: List<Int>,
        fragmentManager: FragmentManager,
        containerId: Int,
        intent: Intent
) {
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(
                fragmentManager,
                fragmentTag,
                navGraphId
        )
        // Handle Intent
        navHostFragment.navController.handleDeepLink(intent)

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


private fun obtainNavHostFragment(
        fragmentManager: FragmentManager,
        fragmentTag: String,
        navGraphId: Int
): NavHostFragment {
    // If the Nav Host fragment exists, return it
    val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
    existingFragment?.let { return it }

    // Otherwise, create it and return it.
    return NavHostFragment.create(navGraphId)
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

    private val holderItemIds = SparseArray<Long>()

    override fun getItemCount(): Int = navGraphIds.size

    override fun getItemId(position: Int): Long {
        return navGraphIds[position].toLong()
    }

    override fun onBindViewHolder(holder: FragmentViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holderItemIds.append(position, holder.itemId)
    }

    override fun createFragment(position: Int): Fragment{
        return obtainNavHostFragment(activity.supportFragmentManager, getFragmentTag(position), navGraphIds[position])
    }

    internal fun getFragmentTag(position: Int) = "f${holderItemIds[position]}"

}
