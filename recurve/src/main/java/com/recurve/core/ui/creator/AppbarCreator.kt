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
import android.view.ViewGroup
import com.google.android.material.appbar.AppBarLayout
import com.recurve.core.databinding.ToolbarCollapsingRecurveBinding
import com.recurve.core.databinding.ToolbarRecurveBinding
import com.recurve.core.ui.creator.ext.AppbarExt

/**
 * 创建折叠ActonBar内容
 *
 * @className: CollapsingContentCreator
 * @author: tangpj
 * @createTime: 2018/11/28 15:40
 */
interface AppbarCreator{

    /**
     * 接收Appbar配置并且初始化Appbar
     *
     * @method: appbar
     * @author: tpj
     * @createTime: 2018/12/5 21:21
     *
     * @param init Appbar配置
     */
    fun appbar(init: AppbarExt.() -> Unit): AppBarLayout

    fun createToolbar(
            appbarExt: AppbarExt,
            inflater: LayoutInflater,
            parent: ViewGroup): ToolbarRecurveBinding

    fun createCollapsingToolbarLayout(
            appbarExt: AppbarExt,
            inflater: LayoutInflater,
            parent: ViewGroup)
            : ToolbarCollapsingRecurveBinding?
}