/*
 * Copyright (C) 2018 Tang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.recurve.dagger2

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.appbar.AppBarLayout
import com.recurve.apollo.dagger2.R
import com.recurve.core.databinding.ActivityRecurveBinding
import com.recurve.core.databinding.FragmentNavigationBinding
import com.recurve.core.ui.creator.AppbarCreator
import com.recurve.core.ui.creator.ContentCreate
import com.recurve.core.ui.creator.RecurveAppbarCreator
import com.recurve.core.ui.creator.RecurveContentCreate
import com.recurve.core.ui.creator.ext.AppbarExt

import dagger.android.support.DaggerAppCompatActivity

abstract class RecurveDaggerActivity:
        DaggerAppCompatActivity(), ContentCreate {

    private lateinit var activityRecurveBinding: ActivityRecurveBinding

    private lateinit var appbarCreator: AppbarCreator
    private lateinit var contentCreate: ContentCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecurveBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_recurve)
        activityRecurveBinding.lifecycleOwner = this
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
        val fragment = NavHostFragment.create(graphResId)
        supportFragmentManager.beginTransaction().add(resId, fragment).commitNow()
        return fragment.navController

    }

    fun appbar(init: AppbarExt.() -> Unit) = appbar(appbarCreator, init)
}

fun appbar(appbarCreator: AppbarCreator,
                             init: AppbarExt.() -> Unit): AppBarLayout
        = appbarCreator.appbar(init)