package com.recurve.mvvmrecurve.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.recurve.adapter.ModulesAdapter
import com.recurve.adapter.creator.ItemCreator
import com.recurve.sample.databinding.BookItemBinding

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