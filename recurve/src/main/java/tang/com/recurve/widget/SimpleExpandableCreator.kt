package tang.com.recurve.widget

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import tang.com.recurve.R

/**
 * Created by tang on 2018/3/18.
 */

class SimpleExpandableCreator<Parent,Child>(
        adapter: ModulesAdapter
        , creatorType: Int = 0
        , @LayoutRes private val parentLayoutId: Int = R.layout.item_text
        , @LayoutRes private val childLayoutId: Int = R.layout.item_text
        , @IdRes private val parentViewId: Int = R.id.text1
        , @IdRes private val childViewId: Int = R.id.text1
        , private val parentConverter: ((Parent?) -> String)? = null
        , private val childConverter: ((Child?) -> String)? = null)
    : ExpandableCreator<Parent, Child
        , SimpleExpandableCreator.ParentViewHolder, SimpleExpandableCreator.ChildViewHolder>(adapter,creatorType = creatorType) {

    override fun onCreateParentViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
            = ParentViewHolder(LayoutInflater.from(parent.context)
            .inflate(parentLayoutId,parent,false), parentViewId)

    override fun onCreateChildViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
            = ChildViewHolder(LayoutInflater.from(parent.context)
            .inflate(childLayoutId,parent,false), childViewId)

    override fun onBindParentItemView(parentHolder: ParentViewHolder, parent: Parent?
                                      , parentPosition: Int, creatorPosition: Int) {
        parentHolder.textView.text = parentConverter?.invoke(parent) ?: parent.toString()
    }

    override fun onBindChildItemView(childHolder: ChildViewHolder, child: Child?
                                     , childPosition: Int, creatorPosition: Int) {
        childHolder.textView.text = childConverter?.invoke(child) ?: child.toString()
    }


    class ParentViewHolder(itemView: View, @IdRes private val viewId: Int): RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(viewId)
    }

    class ChildViewHolder(itemView: View, @IdRes private val viewId: Int): RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(viewId)
    }
}