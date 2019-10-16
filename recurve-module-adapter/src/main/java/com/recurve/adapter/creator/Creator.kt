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
package com.recurve.adapter.creator

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.recurve.adapter.ModulesAdapter

/**
 * Created by tang on 2018/3/11.
 */
interface Creator<Binding: ViewDataBinding>{

    fun onBindCreator(adapter: ModulesAdapter)

    fun getItemCount(): Int

    fun getCreatorItemViewTypeByPosition(creatorPosition: Int): Int

    fun getCreatorItemViewTypeByViteType(viewType: Int): Int

    fun getCreatorType(): Int

    fun getSpan(): Int

    fun onCreateItemBinding(parent: ViewGroup, viewType: Int): Binding

    fun onBindItemView(itemHolder: RecurveViewHolder<*>, creatorPosition: Int)
}
