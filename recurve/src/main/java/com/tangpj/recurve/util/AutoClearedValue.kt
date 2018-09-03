/*
 * Copyright (C) 2017 The Android Open Source Project
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
package com.tangpj.recurve.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


/**
 * Created by tang on 2018/3/5.
 * A value holder that automatically clears the reference if the Fragment's view is destroyed.
 * @param <T>
</T> */
class AutoClearedValue<out T>(fragment: Fragment, private var value: T?) {

    init {
        val fragmentManager = fragment.fragmentManager
        fragmentManager?.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {

                    }
                }, false)
    }

    fun get(): T? = value
}
