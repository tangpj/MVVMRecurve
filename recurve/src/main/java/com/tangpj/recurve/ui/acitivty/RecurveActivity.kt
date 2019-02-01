package com.tangpj.recurve.ui.acitivty

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.tangpj.recurve.R
import com.tangpj.recurve.databinding.ActivityRecurveBinding
import com.tangpj.recurve.databinding.FragmentNavigationBinding
import com.tangpj.recurve.ui.appbar
import com.tangpj.recurve.ui.creator.AppbarCreator
import com.tangpj.recurve.ui.creator.ContentCreate
import com.tangpj.recurve.ui.creator.RecurveAppbarCreator
import com.tangpj.recurve.ui.creator.RecurveContentCreate
import com.tangpj.recurve.ui.creator.ext.AppbarExt
import java.lang.NullPointerException

abstract class RecurveActivity:
        AppCompatActivity(), ContentCreate {

    private lateinit var activityRecurveBinding: ActivityRecurveBinding

    private lateinit var appbarCreator: AppbarCreator
    private lateinit var contentCreate: ContentCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecurveBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_recurve)
        contentCreate = RecurveContentCreate(activityRecurveBinding)
        appbarCreator = RecurveAppbarCreator(this, activityRecurveBinding)
    }

    override fun <Binding : ViewDataBinding> initContentBinding(@LayoutRes layoutId: Int): Binding
            = contentCreate.initContentBinding(layoutId)


    @JvmOverloads
    fun initContentFragment(
            @NavigationRes graphResId: Int,
            @LayoutRes layoutId: Int = R.layout.fragment_navigation,
            @IdRes resId: Int = R.id.fragment_container): NavController =
            initContentFragment<FragmentNavigationBinding>(graphResId, layoutId, resId, null)


    fun <Binding : ViewDataBinding> initContentFragment(
            @NavigationRes graphResId: Int,
            @LayoutRes layoutId: Int = R.layout.fragment_navigation,
            @IdRes resId: Int = R.id.fragment_container,
            initBinding: ((Binding) -> Unit)? = null ): NavController{

        val binding: Binding = initContentBinding(layoutId)
        initBinding?.invoke(binding)
        val fragment = supportFragmentManager.findFragmentById(resId) as? NavHostFragment
                ?: throw NullPointerException("can not find fragment by id = $resId")
        fragment.setGraph(graphResId)
        return findNavController(resId)

    }

    fun appbar(init: AppbarExt.() -> Unit){
        appbar(appbarCreator, init)
    }
}