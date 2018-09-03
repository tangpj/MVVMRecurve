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

/**
 * Created by tang on 2018/3/16.
 * 二级数据操作类
 */
interface ExpandableOperator<Parent,Child> {

    fun setDataList(dataMap: LinkedHashMap<Parent,MutableList<Child>>)

    fun getData(): LinkedHashMap<Parent,MutableList<Child>>

    fun addParentItem(parent: Parent): List<Child>?

    fun addParentItem(parentPosition: Int, parent: Parent): List<Child>?

    fun setParentItem(parentPosition: Int, parent: Parent): List<Child>?

    fun removedParentItem(parent: Parent)

    fun removedParentItemAt(parentPosition: Int)

    fun addChildItem(parent: Parent, child: Child): Boolean

    fun addChildItem(parentPosition: Int, child: Child): Boolean

    fun addChildItem(parent: Parent, childPosition: Int, child: Child)

    fun addChildItem(parentPosition: Int, childPosition: Int, child: Child)

    fun setChildItem(parent: Parent, childPosition: Int, child: Child): Child

    fun setChildItem(parentPosition: Int, childPosition: Int, child: Child): Child

    fun removedChildItem(parent: Parent, child: Child): Boolean

    fun removedChildItem(parentPosition : Int,child: Child): Boolean

    fun removedChildItemAt(parent: Parent, childPosition: Int): Child

    fun removedChildItemAt(parentPosition: Int, childPosition: Int): Child

    fun getParentItemCount(): Int

    fun getChildItemCountByParent(parent: Parent): Int
}