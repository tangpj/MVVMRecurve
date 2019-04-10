package com.tangpj.adapter.creator

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

interface ExpandableBindingView<
        Parent,
        Child,
        ParentBinding: ViewDataBinding,
        ChildBinding: ViewDataBinding >{

    fun onCreateParentViewHolder(parent: ViewGroup): RecurveViewHolder<*>

    fun onCreateChildViewHolder(parent: ViewGroup): RecurveViewHolder<*>

    fun onBindParentItemView(parentHolder: RecurveViewHolder<ParentBinding>?, parent: Parent?
                             , parentPosition: Int, creatorPosition: Int)

    fun onBindChildItemView(childHolder: RecurveViewHolder<ChildBinding>?, child: Child?
                            , childPosition: Int, creatorPosition: Int)
}