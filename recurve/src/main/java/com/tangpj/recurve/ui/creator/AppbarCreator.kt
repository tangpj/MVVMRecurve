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
package com.tangpj.recurve.ui.creator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.tangpj.recurve.databinding.ToolbarCollapsingRecurveBinding
import com.tangpj.recurve.databinding.ToolbarRecurveBinding
import com.tangpj.recurve.ui.creator.ext.AppbarExt
import com.tangpj.recurve.ui.creator.ext.CollapsingToolbarLayoutExt
import com.tangpj.recurve.ui.creator.ext.ToolbarExt

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