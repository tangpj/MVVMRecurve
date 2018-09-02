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

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by tang on 2018/3/15.
 * 辅助创建二级Adapter
 */

abstract class ExpandableCreator<Parent,Child, in ParentHolder: RecyclerView.ViewHolder
        , in ChildHolder: RecyclerView.ViewHolder>
@JvmOverloads constructor(private val adapter: ModulesAdapter, private val creatorType: Int = 0)
    :Creator,ExpandableOperator<Parent,Child> {

    /**
     * ItemType保留位
     * 通过该位确认Item的分级
     */

    companion object {
        const val ITEM_TYPE_PARENT = 1023
        const val ITEM_TYPE_CHILD = 1024
    }

    private var dataMap: LinkedHashMap<Parent,MutableList<Child>> = LinkedHashMap()

    private var parentClickListener:
            ((view: View, parent: Parent?, parentPosition: Int, inCreatorPosition: Int) -> Unit)? = null

    private var childClickListener:
            ((view: View, child: Child, childPosition: Int, inCreatorPosition: Int) -> Unit)? = null

    fun setOnParentClickListener(listener: (view: View, parent: Parent?
                                            , parentPosition: Int, inCreatorPosition: Int) -> Unit){
        parentClickListener = listener
    }

    fun setOnChildClickListener(listener: (view: View, child: Child
                                           , childPosition: Int, inCreatorPosition: Int) -> Unit){
        childClickListener = listener
    }

    override fun setDataList(dataMap: LinkedHashMap<Parent, MutableList<Child>>) {
        this.dataMap = dataMap
        adapter.notifyModulesItemSetChange(this)
    }

    override fun getData(): LinkedHashMap<Parent, MutableList<Child>> = LinkedHashMap(dataMap)

    override fun addParentItem(parent: Parent): List<Child>? {
        val child = dataMap.put(parent, mutableListOf())
        adapter.notifyModulesItemInserted(this, getItemCount() - 1)
        return child
    }

    override fun addParentItem(parentPosition: Int, parent: Parent): List<Child>? {
        return realSetParentItem(parentPosition,parent,true)
    }

    override fun setParentItem(parentPosition: Int, parent: Parent): List<Child>?{
        return realSetParentItem(parentPosition,parent)
    }

    override fun removedParentItem(parent: Parent) {
        val childList = dataMap[parent]
        val aimsStartPosition = getParentPositionInCreator(parent)
        val aimsEnePosition = aimsStartPosition + (childList?.size ?: 0)
        dataMap.remove(parent)
        adapter.notifyModulesItemRangeRemoved(this,aimsStartPosition,aimsEnePosition)
    }

    override fun removedParentItemAt(parentPosition: Int){
        removedParentItem(getParent(parentPosition))
    }

    override fun addChildItem(parent: Parent, child: Child): Boolean
            = operatorChildItemByParent(parent){ it ->
        val result = it.add(child)
        adapter.notifyModulesItemInserted(this, getChildPositionInCreator(parent,child))
        result
    }

    override fun addChildItem(parentPosition: Int, child: Child): Boolean
            = operatorChildItemByParentPosition(parentPosition){ it ->
        val result = it.add(child)
        adapter.notifyModulesItemInserted(this, getChildPositionInCreatorAt(parentPosition,child))
        result
    }

    override fun addChildItem(parent: Parent, childPosition: Int, child: Child)
            = operatorChildItemByParent(parent) { it ->
        it.add(childPosition,child)
        adapter.notifyModulesItemInserted(this, getChildPositionInCreator(parent,child))
    }

    override fun addChildItem(parentPosition: Int, childPosition: Int, child: Child)
            = operatorChildItemByParentPosition(parentPosition){ it ->
        it.add(childPosition,child)
        adapter.notifyModulesItemInserted(this, getChildPositionInCreatorAt(parentPosition,child))
    }

    override fun setChildItem(parent: Parent, childPosition: Int, child: Child): Child
            = operatorChildItemByParent(parent) { it ->
        val result = it.set(childPosition,child)
        adapter.notifyModulesItemChanged(this, getChildPositionInCreator(parent,childPosition))
        result
    }

    override fun setChildItem(parentPosition: Int, childPosition: Int, child: Child): Child
            = operatorChildItemByParentPosition(parentPosition){ it ->
        val result = it.set(childPosition,child)
        adapter.notifyModulesItemChanged(this, getChildPositionInCreatorAt(parentPosition,childPosition))
        result
    }

    override fun removedChildItem(parent: Parent, child: Child): Boolean
            = operatorChildItemByParent(parent){ it ->
        val result = it.remove(child)
        adapter.notifyModulesItemRemoved(this, getChildPositionInCreator(parent,child))
        result
    }

    override fun removedChildItem(parentPosition: Int, child: Child): Boolean
            = operatorChildItemByParentPosition(parentPosition){ it ->
        val result = it.remove(child)
        adapter.notifyModulesItemRemoved(this, getChildPositionInCreatorAt(parentPosition, child))
        result
    }

    override fun removedChildItemAt(parent: Parent, childPosition: Int): Child
            = operatorChildItemByParent(parent){ it ->
        val result = it.removeAt(childPosition)
        adapter.notifyModulesItemRemoved(this, getChildPositionInCreator(parent, childPosition))
        result
    }

    override fun removedChildItemAt(parentPosition: Int, childPosition: Int): Child
            = operatorChildItemByParentPosition(parentPosition){ it ->
        val result = it.removeAt(childPosition)
        adapter.notifyModulesItemRemoved(this, getChildPositionInCreatorAt(parentPosition, childPosition))
        result
    }

    override fun getParentItemCount(): Int = dataMap.size

    override fun getChildItemCountByParent(parent: Parent): Int
            = dataMap[parent]?.size ?: throw NullPointerException("can not find this parent: $parent")


    override fun getItemCount(): Int = dataMap.entries.sumBy { it.value.size } + getParentItemCount()

    override fun getCreatorItemViewTypeByPosition(creatorPosition: Int): Int {
        if (getParentInCreatorPosition(creatorPosition) != null){
            return creatorType * ITEM_TYPE_PARENT
        }
        return creatorType * ITEM_TYPE_CHILD
    }

    override fun getCreatorItemViewTypeByViteType(viewType: Int): Int {
        if (viewType % ITEM_TYPE_PARENT == 0 || viewType % ITEM_TYPE_CHILD == 0){
            return viewType
        }
        return 0
    }

    override fun getCreatorType(): Int {
        return creatorType
    }

    override fun getSpan(): Int = WRAP

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType / creatorType == ITEM_TYPE_PARENT){
            return onCreateParentViewHolder(parent)
        }
        return onCreateChildViewHolder(parent)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindItemView(itemHolder: RecyclerView.ViewHolder, creatorPosition: Int) {
        if (getCreatorItemViewTypeByPosition(creatorPosition) / creatorType == ITEM_TYPE_PARENT){
            val parent = getParentInCreatorPosition(creatorPosition)
            val parentPosition = getParentPosition(parent)
            parentClickListener?.let { listener ->
                itemHolder.itemView.setOnClickListener { listener.invoke(it, parent, parentPosition,creatorPosition) } }
            onBindParentItemView(itemHolder as ParentHolder
                    , parent , parentPosition, creatorPosition)
            return
        }
        val (child,childPosition) = getChild(creatorPosition)
        childClickListener?.let { listener ->
            itemHolder.itemView.setOnClickListener { listener.invoke(it, child, childPosition, creatorPosition) }
        }
        onBindChildItemView(itemHolder as ChildHolder, child, childPosition, creatorPosition)
    }

    private fun realSetParentItem(parentPosition: Int, parent: Parent,isAdd: Boolean = false): List<Child>?{
        val operatorMap  = LinkedHashMap<Parent,MutableList<Child>>()
        var result: MutableList<Child>? = null
        if (dataMap.size < parentPosition){
            dataMap.entries.forEachIndexed{ index, (p, child) ->
                if (index == parentPosition){
                    result = operatorMap.put(parent, mutableListOf())
                    if (isAdd) operatorMap[p] = child
                }else{
                    operatorMap[p] = child
                }
            }
        }else{
            throw IndexOutOfBoundsException("Invalid index $parentPosition, size is ${dataMap.size}")
        }
        dataMap = operatorMap
        adapter.notifyModulesItemInserted(this,getParentPositionInCreator(parent))
        return result?.toList()
    }

    private fun <R> operatorChildItemByParentPosition(
            parentPosition: Int, operator: (childList: MutableList<Child>) -> R): R{
        val parent = getParent(parentPosition)
        val childList = dataMap[parent]
                ?: throw NullPointerException("can not find parent in position $parentPosition")
        return  operator.invoke(childList)
    }

    private fun <R> operatorChildItemByParent(
            parent: Parent, operator: (childList: MutableList<Child>) -> R): R{
        val childList = dataMap[parent] ?: throw NullPointerException("can not find this parent: $parent")
        return operator.invoke(childList)
    }

    private fun getParentPositionInCreator(mParent: Parent): Int{
        var parentPositionInCreator = 0
        dataMap.entries.forEach { (parent, child) ->
            if (mParent == parent) return@forEach
            else parentPositionInCreator += child.size + 1
        }
        return parentPositionInCreator
    }

    private fun getParentPosition(mParent: Parent?): Int{
        dataMap.entries.forEachIndexed { index,(parent, _) ->
            if (mParent == parent) return index
        }
        throw NullPointerException("can not find parent: $mParent")
    }

    private fun getParent(parentPosition: Int): Parent{
        if (parentPosition < dataMap.size){
            dataMap.entries.forEachIndexed{index, mutableEntry ->
                if (index == parentPosition){
                    return mutableEntry.key
                }
            }
        }
        throw IndexOutOfBoundsException("Invalid index $parentPosition, size is ${dataMap.size}")
    }

    private fun getChild(creatorPosition: Int): Pair<Child,Int>{
        var parentPosition = 0
        dataMap.entries.forEach { it ->
            parentPosition += (it.value.size + 1)
            if (parentPosition > creatorPosition){
                val childPosition = creatorPosition - (parentPosition - it.value.size)
                return Pair(it.value[childPosition],childPosition)
            }
        }
        throw NullPointerException("can not find child by position: $creatorPosition")
    }

    private fun getParentInCreatorPosition(creatorPosition: Int): Parent?{
        var position = 0
        dataMap.entries.forEach{
            mutableEntry ->
            if (creatorPosition != position ){
                position += mutableEntry.value.size + 1
            }else{
                return mutableEntry.key
            }
        }
        return null
    }

    private fun getChildPositionInCreator(mParent: Parent, child: Child): Int{
        val childPositionInList: Int = dataMap[mParent]?.indexOf(child) ?: 0
        return getParentPositionInCreator(mParent) + childPositionInList + 1
    }

    private fun getChildPositionInCreatorAt(parentPosition: Int, child: Child): Int =
            getChildPositionInCreator(getParent(parentPosition),child)

    private fun getChildPositionInCreator(mParent: Parent, childPositionInList: Int): Int
            = getParentPositionInCreator(mParent) + childPositionInList + 1

    private fun getChildPositionInCreatorAt(parentPosition: Int, childPositionInList: Int): Int =
            getChildPositionInCreator(getParent(parentPosition),childPositionInList)

    abstract fun onCreateParentViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    abstract fun onCreateChildViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    abstract fun onBindParentItemView(parentHolder: ParentHolder, parent: Parent?
                                      , parentPosition: Int, creatorPosition: Int)

    abstract fun onBindChildItemView(childHolder: ChildHolder, child: Child?
                                     , childPosition: Int, creatorPosition: Int)

}