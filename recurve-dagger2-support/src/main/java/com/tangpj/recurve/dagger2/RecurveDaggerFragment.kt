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
package com.tangpj.recurve.dagger2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tangpj.recurve.ui.creator.LoadingCreator
import com.tangpj.recurve.ui.creator.RecurveLoadingCreator
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


open class RecurveDaggerFragment
    :  Fragment(), HasSupportFragmentInjector, LoadingCreator by RecurveLoadingCreator(){

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    open fun onCreateBinding(inflater: LayoutInflater,
                                 container: ViewGroup?,
                                 savedInstanceState: Bundle?): ViewDataBinding?{
        return null
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = onCreateBinding(inflater, container, savedInstanceState)
        return binding?.root
    }


    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }



    override fun supportFragmentInjector(): AndroidInjector<Fragment>? = childFragmentInjector
}