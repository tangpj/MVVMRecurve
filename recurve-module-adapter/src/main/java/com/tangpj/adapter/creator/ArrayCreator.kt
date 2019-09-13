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
package com.tangpj.adapter.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tangpj.adapter.R
import com.tangpj.adapter.databinding.ItemTextBinding

/**
 * Created by tang on 2018/3/14.
 * 队列Creator
 * @param E 数据实体类
 * @param adapter
 * @param layoutId
 * @param viewId
 * @param stringConverter 字符串转换器
 * @param creatorType adapter中Item的类型
 * 当T的toString不满足需求时可以通过转换器来定义转换规则
 */
class ArrayCreator<E>(
        @LayoutRes private val layoutId: Int = R.layout.item_text,
        @IdRes private val viewId: Int = R.id.text1,
        creatorType: Int = 0,
        private val stringConverter: ((E) -> String)? = null)
    : ItemCreator<E, ViewDataBinding>(creatorType) {

    override fun onCreateItemBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val inflater =  LayoutInflater.from((parent.context))
        val binding = DataBindingUtil.bind<ViewDataBinding>(
                inflater.inflate(layoutId, parent, false))
        return binding ?: ItemTextBinding.inflate(inflater, parent, false)
    }

    override fun onBindItemView(
            binding: ViewDataBinding, e: E, inCreatorPosition: Int) {
        e?.let { val text = stringConverter?.invoke(it) ?: it.toString()
            binding.root.findViewById<TextView>(viewId)?.text = text
        }
    }

}

fun stringCreator(creatorType: Int = 0): ArrayCreator<String>{
    return ArrayCreator(creatorType = creatorType)
}