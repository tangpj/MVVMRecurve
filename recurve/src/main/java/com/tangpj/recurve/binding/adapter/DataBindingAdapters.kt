package com.tangpj.recurve.binding.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class DataBindingAdapter<E, Binding: ViewDataBinding>
    : RecyclerView.Adapter<DataBindingAdapter.BindingViewHolder>(){

    private var dataList = mutableListOf<E>()

    var onItemClickListener : ((View, E, Int) -> Unit)? = null
    
    open fun addItem(item: E){
        dataList.add(item)
        notifyItemInserted(itemCount - 1)
    }

    open fun removedItem(item: E) : Boolean{
        val success = dataList.remove(item)
        notifyItemRemoved(itemCount -1)
        return success
    }
    
    open fun removeItems(items: List<E>){
        dataList.removeAll(items)
        notifyItemRangeRemoved(itemCount - 1 - items.size, itemCount)
    }
    
    open fun removeItemRange(
            start: Int, itemCount: Int, 
            removedListener: ((E) -> Unit)? = null ){
        val removedList = dataList.filterIndexed { index, e ->
            if (index >= start && index < start + itemCount){
                removedListener?.invoke(e)
                true
            }else{
                false
            }
        }
        dataList.removeAll(removedList)
        notifyItemRangeRemoved(start, itemCount)
    }

    open fun setDataList(list: List<E>){
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = onCreateBinding(parent, viewType)
        return BindingViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(it, dataList[position], position)
        }
        onBindBinding(holder.binding as Binding, dataList[position], position)
    }

    abstract fun onCreateBinding(parent: ViewGroup, viewType: Int): Binding

    abstract fun onBindBinding(binding: Binding, e: E, position: Int)


    class BindingViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

}

