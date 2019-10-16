package com.recurve.adapter.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.recurve.adapter.R
import com.recurve.adapter.databinding.ItemTextBinding

/**
 * Created by tang on 2018/3/18.
 */

class SimpleExpandableCreator<Parent,Child>(
        creatorType: Int = 0,
        @LayoutRes private val parentLayoutId: Int = R.layout.item_text,
        @LayoutRes private val childLayoutId: Int = R.layout.item_text,
        @IdRes private val parentViewId: Int = R.id.text1,
        @IdRes private val childViewId: Int = R.id.text1,
        private val parentConverter: ((Parent?) -> String)? = null,
        private val childConverter: ((Child?) -> String)? = null)
    : ExpandableCreator<Parent, Child,
        ViewDataBinding, ViewDataBinding>(creatorType = creatorType) {

    override fun onCreateParentBinding(parent: ViewGroup): ViewDataBinding{
        val inflater =  LayoutInflater.from((parent.context))
        val binding = DataBindingUtil.bind<ViewDataBinding>(
                inflater.inflate(parentLayoutId, parent, false))
        return binding ?: ItemTextBinding.inflate(inflater, parent, false)
    }


    override fun onCreateChildBinding(parent: ViewGroup): ViewDataBinding{
        val inflater =  LayoutInflater.from((parent.context))
        val binding = DataBindingUtil.bind<ViewDataBinding>(
                inflater.inflate(childLayoutId, parent, false))

        return binding ?: ItemTextBinding.inflate(inflater, parent, false)

    }

    override fun onBindParentItemView(parentHolder: RecurveViewHolder<ViewDataBinding>?, parent: Parent?
                                      , parentPosition: Int, creatorPosition: Int) {
        parent?.let { val text = parentConverter?.invoke(it) ?: it.toString()
            parentHolder?.itemView?.findViewById<TextView>(parentViewId)?.text = text
        }
    }

    override fun onBindChildItemView(childHolder: RecurveViewHolder<ViewDataBinding>?, child: Child?
                                     , childPosition: Int, creatorPosition: Int) {
        child?.let { val text = childConverter?.invoke(it) ?: it.toString()
            childHolder?.itemView?.findViewById<TextView>(parentViewId)?.text = text
        }
    }
}