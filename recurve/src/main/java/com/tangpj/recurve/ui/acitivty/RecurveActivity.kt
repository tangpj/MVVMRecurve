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
            initContentFragment<ViewDataBinding>(graphResId, layoutId, null)


    fun <Binding : ViewDataBinding> initContentFragment(
            @NavigationRes graphResId: Int,
            @LayoutRes layoutId: Int = R.layout.fragment_navigation,
            initBinding: ((Binding) -> Unit)? = null ): NavController{

        val binding: Binding = initContentBinding(layoutId)
        initBinding?.invoke(binding)
        val fragment = NavHostFragment.create(graphResId)
        return fragment.navController

    }

    fun appbar(init: AppbarExt.() -> Unit){
        appbar(appbarCreator, init)
    }
}