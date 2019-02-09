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

import androidx.recyclerview.widget.RecyclerView
import com.tangpj.recurve.recyclerview.creator.Creator


/**
 * 列表布局创建器
 *
 * @className: ListViewCreator
 * @author: tpj
 * @createTime: 2019/1/17 20:46
 */
interface RecyclerViewInit{

    /**
     * 添加内容创建器
     *
     * @method: addItemCreator
     * @author: tpj
     * @createTime: 2019/1/17 21:07
     * @param creator Item内容创建器
     */
    fun addItemCreator(creator: Creator)

    /**
     * 添加内容创建器到指定位置
     *
     * @method: addItemCreator
     * @author: tpj
     * @createTime: 2019/1/17 21:08
     */
    fun addItemCreator(index: Int, creator: Creator)

    fun setLayoutManager(lm: RecyclerView.LayoutManager)

}