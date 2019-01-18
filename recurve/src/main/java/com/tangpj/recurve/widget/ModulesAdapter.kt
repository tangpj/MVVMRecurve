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

import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.recyclerview.widget.RecyclerView
import java.util.function.Function

/**
 * Created by tang on 2018/3/10.
 */
class ModulesAdapter
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var creatorList: MutableList<Creator>
            = mutableListOf()

    fun setCreator(creatorList: MutableList<Creator>){
        checkedViewType { it ->
            it.entries.forEach { entry ->
                if ( entry.value.size > 1){
                    return@checkedViewType true
                }
            }
            return@checkedViewType  false
        }

        this.creatorList = creatorList
        notifyDataSetChanged()
    }

    fun addCreator(creator: Creator){
        checkedViewType{
            it[creator.getCreatorType()] != null
        }
        creatorList.add(creator)
        notifyModulesItemSetChange(creator)
    }

    fun addCreator(index: Int, creator: Creator){
        checkedViewType{
            it[creator.getCreatorType()] != null
        }
        creatorList.add(index, creator)
        notifyModulesItemSetChange(creator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val viewTypeList = creatorList.groupBy { it.getCreatorItemViewTypeByViteType(viewType)}[viewType]
        return viewTypeList?.first()?.onCreateItemViewHolder(parent, viewType)
                ?: creatorList.first().onCreateItemViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val creator = creatorList[getCreatorIndex(position)]
        val modulesStartPosition = getModulesStartPosition(creator)
        val inCreatorPosition = position - modulesStartPosition
        creator.onBindItemView(holder,inCreatorPosition)
    }

    override fun getItemCount(): Int  = creatorList.sumBy { it -> it.getItemCount() }

    override fun getItemViewType(position: Int): Int {
        var sum = 0
        creatorList.forEach {
            sum += it.getItemCount()
            if (sum > position)
                return@getItemViewType it.getCreatorItemViewTypeByPosition(getCreatorPosition(position))
        }
        return -1
    }

    fun notifyModulesItemSetChange(creator: Creator){
        notifyModulesItemRangeChange(creator ,0,creator.getItemCount() - 1)
    }

    fun notifyModulesItemRangeChange(creator: Creator, aimsStartPosition: Int, aimsEndPosition: Int){
        val startPosition = getModulesStartPosition(creator)
        notifyItemRangeChanged(startPosition + aimsStartPosition,
                startPosition + aimsEndPosition)
    }

    fun notifyModulesItemChanged(creator: Creator, aimsPosition: Int){
        val startPosition = getModulesStartPosition(creator)
        notifyItemChanged(startPosition + aimsPosition)
    }

    fun notifyModulesItemInserted(creator: Creator, aimsPosition: Int){
        val startPosition = getModulesStartPosition(creator)
        notifyItemInserted(startPosition + aimsPosition)
    }

    fun notifyModulesItemRemoved(creator: Creator, aimsPosition: Int){
        val startPosition = getModulesStartPosition(creator)
        notifyItemRemoved(startPosition + aimsPosition)
    }

    fun notifyModulesItemRangeRemoved(creator: Creator, aimsStartPosition: Int, aimsEndPosition: Int){
        val startPosition = getModulesStartPosition(creator)
        notifyItemRangeRemoved(startPosition + aimsStartPosition
                , startPosition + aimsEndPosition)
    }

    private fun getModulesStartPosition(creator: Creator): Int{
        val creatorPosition = creatorList.indexOf(creator)
        var startPosition = 0
        creatorList.forEachIndexed { index, iCreator ->
            if( index == creatorPosition) return startPosition
            else startPosition += iCreator.getItemCount()
        }
        return startPosition
    }

    private fun getCreatorIndex(position: Int): Int{
        var startPosition = 0
        var resultIndex = 0
        creatorList.forEachIndexed { index, iCreator ->
            startPosition += iCreator.getItemCount()
            if( startPosition > position){
                resultIndex = index
                return resultIndex
            }
        }
        return resultIndex
    }

    private fun getCreatorPosition(position: Int): Int{
        var startPosition = 0
        var resultPosition = 0
        creatorList.forEach {iCreator ->
            if( startPosition + iCreator.getItemCount() <= position){
               startPosition += iCreator.getItemCount()
            }else{
                resultPosition = position - startPosition
                return@forEach
            }
        }
        return resultPosition
    }

    private fun  checkedViewType(checkFun: (Map<Int, List<Creator>>) -> Boolean){
        val creatorMap = creatorList.groupBy { it.getCreatorType() }
        if(checkFun.invoke(creatorMap)){
            throw IllegalArgumentException("Creator CreatorType can't not equal")
        }
    }

}

const val FILL = -1
const val WRAP = -2

@IntDef(FILL, WRAP)
annotation class SpanType


