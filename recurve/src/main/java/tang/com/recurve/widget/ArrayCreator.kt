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
package tang.com.recurve.widget

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tang.com.recurve.R

/**
 * Created by tang on 2018/3/14.
 * 队列Creator
 * @param T 数据实体类
 * @param adapter
 * @param layoutId
 * @param viewId
 * @param stringConverter 字符串转换器
 * @param itemType adapter中Item的类型
 * 当T的toString不满足需求时可以通过转换器来定义转换规则
 */
class ArrayCreator<T>(adapter: ModulesAdapter
                      , @LayoutRes private val layoutId: Int = R.layout.item_text
                      , @IdRes private val viewId: Int = R.id.text1
                      , creatorType: Int = 0
                      , private val stringConverter: ((T) -> String)? = null)
    : ItemCreator<T, ArrayCreator.ArrayViewHolder>(adapter,creatorType) {

    override fun onCreateItemViewHolder(parent: ViewGroup,viewType: Int): ArrayViewHolder {
        return ArrayViewHolder(LayoutInflater.from(parent.context).inflate(layoutId,parent,false),viewId)
    }

    override fun onBindItemView(itemHolder: ArrayViewHolder, e: T, inCreatorPosition: Int) {
        itemHolder.textView.text = stringConverter?.invoke(e) ?: e.toString()
    }

    class ArrayViewHolder(itemView: View, @IdRes private val viewId: Int): RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(viewId)
    }
}

fun stringCreator(adapter: ModulesAdapter,creatorType: Int = 0): ArrayCreator<String>{
    return ArrayCreator(adapter, creatorType = creatorType)
}