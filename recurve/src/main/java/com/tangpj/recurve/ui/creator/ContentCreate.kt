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

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

/**
 * 内容创建器
 * 
 * @className: ContentCreate
 * @author: tpj
 * @createTime: 2019/1/17 20:53
 */
interface ContentCreate{

    //根据Id初始化ContentBinding
    fun <Binding : ViewDataBinding> initContentBinding(@LayoutRes layoutId: Int): Binding
}