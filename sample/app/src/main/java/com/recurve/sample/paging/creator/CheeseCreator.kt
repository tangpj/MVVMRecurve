package com.recurve.sample.paging.creator

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.recurve.sample.paging.vo.Cheese
import com.recurve.paging.PagedItemCreator
import com.recurve.sample.databinding.CheeseItemBinding

class CheeseCreator(diff: DiffUtil.ItemCallback<Cheese>)
    : PagedItemCreator<Cheese, CheeseItemBinding>(aDiffCallback = diff ){
    override fun onBindItemView(binding: CheeseItemBinding, e: Cheese, inCreatorPosition: Int) {
         binding.cheese = e
        Log.d("CheeseCreator", e.toString())
    }

    override fun onCreateItemBinding(parent: ViewGroup, viewType: Int): CheeseItemBinding {
        val inflater = LayoutInflater.from(parent.context)
        return CheeseItemBinding.inflate(inflater, parent, false)
    }



}