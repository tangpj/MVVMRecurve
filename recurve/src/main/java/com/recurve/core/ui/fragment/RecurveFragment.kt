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
package com.recurve.core.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.recurve.core.ui.creator.LoadingCreator
import com.recurve.core.ui.creator.RecurveLoadingCreator

/**
 * 
 * @className: RecurveFragment
 * @author: tpj
 * @createTime: 2019/1/18 22:45
 */
abstract class RecurveFragment
    : Fragment(), LoadingCreator by RecurveLoadingCreator(){

    abstract fun onCreateBinding(inflater: LayoutInflater,
                                 container: ViewGroup?,
                                 savedInstanceState: Bundle?): ViewDataBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = this.onCreateBinding(inflater, container, savedInstanceState)
        binding.lifecycleOwner = this
        return binding.root
    }

}