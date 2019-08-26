package com.tangpj.viewpager

import android.content.Intent
import android.util.SparseArray
import android.util.SparseIntArray
import androidx.core.util.forEach
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
            val selectedFragment = navFragmentAdapter.getNavContainerFragmentByPosition(position)
            if (navController != null && selectedFragment != null) {
                selectedNavController.value = navController
                selectFragment(fragmentManager, selectedFragment)
            } else {
                navFragmentAdapter.observe(position) { fragment, navController ->
                    selectedNavController.value = navController
                    selectFragment(fragmentManager, fragment)
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

private fun selectFragment(
        fragmentManager: FragmentManager,
        selectedFragment: Fragment){
    fragmentManager.beginTransaction()
            .attach(selectedFragment)
            .setPrimaryNavigationFragment(selectedFragment)
            .setReorderingAllowed(true)
            .commit()

}

private class NavHostPagerAdapter(
        activity: FragmentActivity,
        private val intent: Intent,
        private val navGraphIds: List<Int>
) : FragmentStateAdapter(activity){

    companion object{
        private const val KEY_PREFIX_FRAGMENT = "f"
    }

    private val actionList = SparseArray<((NavContainerFragment, NavController) -> Unit)?>()
    private val navControllerList = SparseArray<NavController>()
    val createdFragmentPosition = SparseIntArray()
    private val fragmentManager = activity.supportFragmentManager

    fun getNavControllerByPosition(position: Int): NavController? =
        navControllerList.get(position)

    fun getNavContainerFragmentByPosition(position: Int) : NavContainerFragment?{
        return fragmentManager.findFragmentByTag(getTag(position)) as? NavContainerFragment
    }

    fun observe(position: Int, action: (NavContainerFragment, NavController) -> Unit){
        actionList.append(position, action)
    }

    override fun getItemCount(): Int = navGraphIds.size

    override fun getItemId(position: Int): Long {
        return navGraphIds[position].toLong()
    }

    override fun createFragment(position: Int): Fragment{
        val fragment =  NavContainerFragment.create(navGraphIds[position])
        // Find or create the Navigation host fragment
        fragment.navHostFragment.observe(fragment, Observer {
            val navController = it.navController
            navController.handleDeepLink(intent)
            navControllerList.append(position, navController)
            actionList[position]?.invoke(fragment, navController)
        })
        createdFragmentPosition.append(position, position)
        return fragment
    }

    // Helper function for dealing with save / restore state
    internal fun getTag(position: Int): String {
        return KEY_PREFIX_FRAGMENT + getItemId(position)
    }
}
