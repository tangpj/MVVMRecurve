package com.tangpj.mvvmrecurve.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.tangpj.adapter.ModulesAdapter
import com.tangpj.adapter.creator.ItemCreator
import com.tangpj.adapter.creator.RecurveViewHolder
import com.tangpj.mvvmrecurve.databinding.BookItemBinding

class BookCreator(adapter: ModulesAdapter) : ItemCreator<Book, BookItemBinding>(2){
    override fun onBindItemView(binding: BookItemBinding, e: Book, inCreatorPosition: Int) {
        binding.book = e
        Log.d("BookCreator", e.toString())
    }



    override fun onCreateItemBinding(parent: ViewGroup, viewType: Int): BookItemBinding {
        val inflater = LayoutInflater.from(parent.context)
        return BookItemBinding.inflate(inflater, parent, false)
    }

}