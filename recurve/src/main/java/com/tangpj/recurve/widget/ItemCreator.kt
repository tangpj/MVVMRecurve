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
package com.tangpj.recurve.widget

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by tang on 2018/3/11.
 * 辅助Adapter创建Item
 */
abstract class ItemCreator<E, in ItemHolder: RecyclerView.ViewHolder> @JvmOverloads constructor(
        private val adapter: ModulesAdapter, private val creatorType: Int = 0): Creator, ArrayDataOperator<E>{

    private var dataList: MutableList<E> = mutableListOf()

    private var itemClickListener: ((view: View, e: E, creatorPosition: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: ((view: View, e: E, creatorPosition: Int) -> Unit)){
        this.itemClickListener = listener
    }

    override fun setDataList(dataList: MutableList<E>){
        this.dataList = dataList
        if (dataList.size > 0){
            adapter.notifyModulesItemSetChange(this)
        }
    }

    override fun getData(): List<E> = dataList.toList()

    final override fun getItem(position: Int): E = dataList[position]

    final override fun addItem(e: E): Boolean{
        val isSucceed = dataList.add(e)
        return if (isSucceed){
            adapter.notifyModulesItemInserted(this,dataList.size - 1)
            true
        }else {
            false
        }
    }

    override fun addItem(position: Int, e: E) {
        dataList.add(position,e)
        adapter.notifyModulesItemInserted(this, position)
    }

    final override fun setItem(position: Int, e: E): E? {
        val result = dataList.set(position,e)
        if (result != null){
            adapter.notifyModulesItemChanged(this,position)
        }
        return result
    }

    final override fun removedItem(e: E): Boolean {
        val removedPosition = dataList.indexOf(e)
        val isSucceed = dataList.remove(e)
        return if (isSucceed){
            adapter.notifyModulesItemRemoved(this,removedPosition)
            true
        }else{
            false
        }
    }

    final override fun removedItemAt(position: Int): E?{
        val removedItem = dataList.removeAt(position)
        if (removedItem != null){
            adapter.notifyModulesItemRemoved(this,position)
        }
        return removedItem
    }

    final override fun getItemCount() = dataList.size

    final override fun getCreatorItemViewTypeByPosition(creatorPosition: Int): Int = creatorType

    override fun getCreatorItemViewTypeByViteType(viewType: Int): Int = creatorType

    final override fun getCreatorType(): Int  = creatorType

    override fun getSpan(): Int = WRAP

    abstract fun onBindItemView(itemHolder: ItemHolder, e: E, inCreatorPosition: Int)

    @Suppress("UNCHECKED_CAST")
    final override fun onBindItemView(itemHolder: RecyclerView.ViewHolder, creatorPosition: Int) {
        val e: E = dataList[creatorPosition]
        itemClickListener?.let { listener
            -> itemHolder.itemView.setOnClickListener {
            listener.invoke(itemHolder.itemView, e , creatorPosition) } }
        onBindItemView(itemHolder as ItemHolder, e ,creatorPosition)
    }

}

