package com.tangpj.viewpager

import android.content.Intent
import android.util.Log
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import androidx.core.util.containsKey
import androidx.core.util.forEach
import androidx.core.util.set
import androidx.core.view.ViewCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.setupWithNavController(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        navGraphIds :List<Int>,
        intent: Intent?,
        fragmentCreator: ((position: Int) -> Pair<Int, (ViewDataBinding) -> Unit>?)? = null)
        : LiveData<((firstInit: Boolean, navController: NavController) -> Unit) -> Unit> {

    id = ViewCompat.generateViewId()
    val selectedNavController = MutableLiveData<NavController>()
    val navFragmentAdapter = NavHostPagerAdapter(fragmentManager,lifecycle, intent, navGraphIds, fragmentCreator)

    //record first init
    val firstInitSet = SparseIntArray(navGraphIds.size)

    adapter = navFragmentAdapter
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            val result = navFragmentAdapter.getNavControllerByPosition(position)
            val selectedFragment = navFragmentAdapter.getNavContainerFragmentByPosition(position)
            if (result != null && selectedFragment != null) {
                selectedNavController.value = result
                selectFragment(fragmentManager, selectedFragment)
            } else {
                navFragmentAdapter.observe(position) { fragment, navController ->
                    selectedNavController.value = navController
                    selectFragment(fragmentManager, fragment)
                }
            }

        }

    })

    // Finally, ensure that we update our ViewPager2 when the back stack changes
    fragmentManager.addOnBackStackChangedListener {
        // Reset the graph if the currentDestination is not valid (happens when the back
        // stack is popped after using the back button).
        selectedNavController.value?.let { controller ->
            if (controller.currentDestination == null) {
                controller.navigate(controller.graph.id)
            }
        }
    }
    val isFirstInitFun = { graphId: Int -> !firstInitSet.containsKey(graphId) }
    return Transformations.map(selectedNavController){ navController ->
        { observerNavController:  (firstInit : Boolean, navController: NavController) -> Unit ->
            val currentId = navController .currentDestination?.id ?: 0
            val isFirstInit = isFirstInitFun(currentId)
            observerNavController.invoke(isFirstInit, navController)
            Log.d("NavViewPagerExtensions", "isFirstInit = $isFirstInit, destination = ${navController.currentDestination?.label}")

            firstInitSet.put(currentId, currentId)
        }
    }
}

fun ViewPager2.setupWithNavController(
        activity: FragmentActivity,
        navGraphIds :List<Int>,
        intent: Intent,
        fragmentCreator: ((position: Int) -> Pair<Int, (ViewDataBinding) -> Unit>?)? = null)
        : LiveData<((firstInit: Boolean, navController: NavController) -> Unit) -> Unit> =
        setupWithNavController(
                activity.supportFragmentManager,
                activity.lifecycle,
                navGraphIds,
                intent,
                fragmentCreator)

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
        val fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        private val intent: Intent?,
        private val navGraphIds: List<Int>,
        val fragmentCreator: ((position: Int) -> Pair<Int, (ViewDataBinding) -> Unit>?)? = null
) : FragmentStateAdapter(fragmentManager, lifecycle){

    companion object{
        private const val KEY_PREFIX_FRAGMENT = "f"
    }

    private val actionList = SparseArray<((NavContainerFragment, NavController) -> Unit)?>()
    private val navControllerList = SparseArray<NavController>()
    val createdFragmentPosition = SparseIntArray()

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
        fragment.initContainer (fragmentCreator?.invoke(position))
        createdFragmentPosition.append(position, position)
        return fragment
    }

    // Helper function for dealing with save / restore state
    internal fun getTag(position: Int): String {
        return KEY_PREFIX_FRAGMENT + getItemId(position)
    }
}
