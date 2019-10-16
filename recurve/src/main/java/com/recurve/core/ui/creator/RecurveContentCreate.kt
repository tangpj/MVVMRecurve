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
package com.recurve.core.ui.creator

import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.appbar.AppBarLayout
import com.recurve.core.databinding.ActivityRecurveBinding

class RecurveContentCreate(var activityRecurveBinding: ActivityRecurveBinding) : ContentCreate {

    override fun <Binding : ViewDataBinding> initContentBinding(@LayoutRes layoutId: Int): Binding {

        val inflater = LayoutInflater.from(activityRecurveBinding.clRoot.context)
        val binding: Binding = DataBindingUtil
                .inflate(inflater, layoutId, activityRecurveBinding.clRoot, false)
        val contentLayoutParams = binding.root.layoutParams
                as? CoordinatorLayout.LayoutParams
        if (contentLayoutParams?.behavior == null){
            contentLayoutParams?.behavior = AppBarLayout.ScrollingViewBehavior()
        }
        activityRecurveBinding.clRoot.addView(binding.root, contentLayoutParams)
        return binding

    }
}