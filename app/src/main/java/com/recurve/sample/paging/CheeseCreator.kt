package com.recurve.sample.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.recurve.adapter.creator.ItemCreator
import com.recurve.mvvmrecurve.paging.Cheese
import com.recurve.paging.PagedItemCreator
import com.recurve.sample.databinding.CheeseItemBinding

class CheeseCreator(diff: DiffUtil.ItemCallback<Cheese>)
    : PagedItemCreator<Cheese, CheeseItemBinding>(aDiffCallback = diff ){
    override fun onBindItemView(binding: CheeseItemBinding, e: Cheese, inCreatorPosition: Int) {
         binding.cheese = e

    }

    override fun onCreateItemBinding(parent: ViewGroup, viewType: Int): CheeseItemBinding {
        val inflater = LayoutInflater.from(parent.context)
        return CheeseItemBinding.inflate(inflater, parent, false)
    }



}