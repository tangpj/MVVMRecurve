package com.tangpj.viewpager

import android.content.Intent
import android.util.SparseArray
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.viewpager2.adapter.FragmentStateAdapter
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
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            val navController = navFragmentAdapter.getNavControllerByPosition(position)
            if (navController != null) {
                selectedNavController.value = navController
            } else {
                navFragmentAdapter.observe(position) {
                    selectedNavController.value = it
                }
            }
        }

    })


    // Finally, ensure that we update our BottomNavigationView when the back stack changes
    fragmentManager.addOnBackStackChangedListener {
        // Reset the graph if the currentDestination is not valid (happens when the back
        // stack is popped after using the back button).
        selectedNavController.value?.let { controller ->
            if (controller.currentDestination == null) {
                controller.navigate(controller.graph.id)
            }
        }
    }
    return selectedNavController
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
        activity: FragmentActivity,
        private val intent: Intent,
        private val navGraphIds: List<Int>
) : FragmentStateAdapter(activity){

    companion object{
        private const val KEY_PREFIX_FRAGMENT = "f"
    }

    private val actionList = SparseArray<((NavController) -> Unit)?>()
    private val navControllerList = SparseArray<NavController>()

    fun observe(position: Int, action: (NavController) -> Unit){
        actionList.append(position, action)
    }

    fun getNavControllerByPosition(position: Int) =
        navControllerList.get(position)

    val fragmentManager = activity.supportFragmentManager

    override fun getItemCount(): Int = navGraphIds.size

    override fun getItemId(position: Int): Long {
        return navGraphIds[position].toLong()
    }

    override fun createFragment(position: Int): Fragment{
        val fragment =  NavContainerFragment.create(navGraphIds[position])
        // Find or create the Navigation host fragment
        fragment.navController.observe(fragment, Observer {
            it.handleDeepLink(intent)
            navControllerList.append(position, it)
            actionList[position]?.invoke(it)
        })
        return fragment
    }

    // Helper function for dealing with save / restore state
    internal fun getTag(position: Int): String {
        return KEY_PREFIX_FRAGMENT + getItemId(position)
    }
}
