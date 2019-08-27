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

import android.view.View
import androidx.databinding.ViewDataBinding
import com.tangpj.adapter.ModulesAdapter
import com.tangpj.adapter.WRAP
import java.lang.IllegalArgumentException
import java.lang.NullPointerException

/**
 * Created by tang on 2018/3/11.
 * 辅助Adapter创建Item
 */
abstract class ItemCreator<E, Binding: ViewDataBinding> @JvmOverloads constructor(
        private val creatorType: Int = 0):
        Creator<Binding>, DataOperator<E>, BindingView<E, Binding>{

    init {
        require(!((creatorType == ExpandableCreator.ITEM_TYPE_PARENT) ||
                (creatorType == ExpandableCreator.ITEM_TYPE_PARENT))) { "creatorType can't equal " +
                "ExpandableCreator.ITEM_TYPE_PARENT(1023) " +
                "or ExpandableCreator.ITEM_TYPE_PARENT(1024)" }
    }

    protected lateinit var mAdapter: ModulesAdapter

    private var dataList: MutableList<E> = mutableListOf()

    private var itemClickListener: ((view: View, e: E, creatorPosition: Int) -> Unit)? = null

    override fun onBindCreator(adapter: ModulesAdapter) {
        this.mAdapter = adapter
    }

    fun setOnItemClickListener(listener: ((view: View, e: E, creatorPosition: Int) -> Unit)){
        this.itemClickListener = listener
    }

    override fun setDataList(dataList: List<E>){
        if (dataList.isNotEmpty()){
            this.dataList.clear()
            this.dataList.addAll(dataList)
            mAdapter.notifyModulesItemSetChange(this)
        }
    }

    override fun getData(): List<E> = dataList.toList()

    final override fun getItem(position: Int): E = dataList[position]

    final override fun addItem(e: E): Boolean{
        val isSucceed = dataList.add(e)
        return if (isSucceed){
            mAdapter.notifyModulesItemInserted(this,dataList.size - 1)
            true
        }else {
            false
        }
    }

    override fun addItem(position: Int, e: E) {
        dataList.add(position,e)
        mAdapter.notifyModulesItemInserted(this, position)
    }

    override fun addItems(items: List<E>) {
        val notifyStart = dataList.size
        dataList.addAll(items)
        mAdapter.notifyModulesItemRangeInserted(this, notifyStart, items.size)
    }

    final override fun setItem(position: Int, e: E): E? {
        val result = dataList.set(position,e)
        if (result != null){
            mAdapter.notifyModulesItemInserted(this,position)
        }
        return result
    }

    final override fun removedItem(e: E): Boolean {
        val removedPosition = dataList.indexOf(e)
        val isSucceed = dataList.remove(e)
        return if (isSucceed){
            mAdapter.notifyModulesItemRemoved(this,removedPosition)
            true
        }else{
            false
        }
    }

    final override fun removedItemAt(position: Int): E?{
        val removedItem = dataList.removeAt(position)
        if (removedItem != null){
            mAdapter.notifyModulesItemRemoved(this,position)
        }
        return removedItem
    }

    override fun getItemCount() = dataList.size

    final override fun getCreatorItemViewTypeByPosition(creatorPosition: Int): Int = creatorType

    override fun getCreatorItemViewTypeByViteType(viewType: Int): Int = creatorType

    final override fun getCreatorType(): Int  = creatorType

    override fun getSpan(): Int = WRAP


    @Suppress("UNCHECKED_CAST")
    final override fun onBindItemView(
            itemHolder: RecurveViewHolder<*>, creatorPosition: Int) {
        if (getItemCount() == 0){
            return
        }
        val e: E = if (dataList.size > creatorPosition){
            dataList[creatorPosition]
        }else{
            throw NullPointerException("can not find node")
        }
        itemClickListener?.let { listener
            -> itemHolder.itemView.setOnClickListener {
            listener.invoke(itemHolder.itemView, e , creatorPosition) } }

        onBindItemView(itemHolder.binding as Binding, e, creatorPosition)
    }

}

