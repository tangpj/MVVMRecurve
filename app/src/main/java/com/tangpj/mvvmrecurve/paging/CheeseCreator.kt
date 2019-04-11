package com.tangpj.mvvmrecurve.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tangpj.adapter.adapter.ModulesAdapter
import com.tangpj.adapter.creator.ItemCreator
import com.tangpj.adapter.creator.RecurveViewHolder
import com.tangpj.mvvmrecurve.databinding.CheeseItemBinding

class CheeseCreator(adapter: ModulesAdapter) : ItemCreator<Cheese, CheeseItemBinding>(adapter){

    override fun onBindItemView(itemHolder: RecurveViewHolder<CheeseItemBinding>?, e: Cheese?, inCreatorPosition: Int) {
        itemHolder?.let {
            it.binding.cheese = e
        }
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecurveViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        return RecurveViewHolder(CheeseItemBinding.inflate(inflater, parent, false))
    }

}