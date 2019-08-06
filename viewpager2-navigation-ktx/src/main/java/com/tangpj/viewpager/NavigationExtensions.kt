package com.tangpj.viewpager

import android.annotation.SuppressLint
import android.content.Intent
import android.util.SparseArray
import android.view.LayoutInflater
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.setupWithNavController(
        activity: FragmentActivity,
        navGraphIds :List<Int>,
        intent: Intent) : LiveData<NavController>{

    id = ViewCompat.generateViewId()
    val selectedNavController = MutableLiveData<NavController>()
    val navFragmentAdapter = NavHostPagerAdapter(activity, intent, navGraphIds )

    val fragmentManager = activity.supportFragmentManager

    adapter = navFragmentAdapter
    setupItemReselected(navFragmentAdapter, fragmentManager)
    fragmentManager.addOnBackStackChangedListener {
    }
    return selectedNavController
}

private fun ViewPager2.setupItemReselected(
        adapter: NavHostPagerAdapter,
        fragmentManager: FragmentManager) {
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            val selectedFragment = fragmentManager.findFragmentByTag(adapter.getKey(position))
                    as NavContainerFragment
            val navController = selectedFragment.getNavController()
            // Pop the back stack to the start destination of the current navController graph
//            navController?.popBackStack(
//                    navController.graph.startDestination, false
//            )
        }
    })
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


private class NavHostPagerAdapter(
        private val activity: FragmentActivity,
        private val intent: Intent,
        private val navGraphIds: List<Int>
) : FragmentStateAdapter(activity){

    companion object{
        private const val KEY_PREFIX_FRAGMENT = "f"
    }

    private val holderItemIds = SparseArray<Long>()
    private val fragmentManager = activity.supportFragmentManager

    override fun getItemCount(): Int = navGraphIds.size

    override fun getItemId(position: Int): Long {
        return navGraphIds[position].toLong()
    }

    override fun onBindViewHolder(holder: FragmentViewHolder, position: Int, payloads: MutableList<Any>) {
        holderItemIds.append(position, holder.itemId)
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun createFragment(position: Int): Fragment{
        val fragment =
                NavContainerFragment.create(navGraphIds[position])
        // Find or create the Navigation host fragment
        fragment.lifecycle.addObserver(object : LifecycleEventObserver{
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_CREATE){
                    fragment.getNavController()?.handleDeepLink(intent)
                }
            }

        })
        return fragment
    }

    // Helper function for dealing with save / restore state
    internal fun getKey(position: Int): String {
        return KEY_PREFIX_FRAGMENT + getItemId(position)
    }
}
