package com.recurve.adapter.creator

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

interface ExpandableBindingView<
        Parent,
        Child,
        ParentBinding: ViewDataBinding,
        ChildBinding: ViewDataBinding >{

    fun onCreateParentBinding(parent: ViewGroup): ParentBinding

    fun onCreateChildBinding(parent: ViewGroup): ChildBinding

    fun onBindParentItemView(parentHolder: RecurveViewHolder<ParentBinding>?, parent: Parent?
                             , parentPosition: Int, creatorPosition: Int)

    fun onBindChildItemView(childHolder: RecurveViewHolder<ChildBinding>?, child: Child?
                            , childPosition: Int, creatorPosition: Int)
}