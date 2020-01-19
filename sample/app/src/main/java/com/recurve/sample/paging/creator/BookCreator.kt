package com.recurve.sample.paging.creator

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.recurve.sample.paging.vo.Book
import com.recurve.paging.PagedItemCreator
import com.recurve.sample.databinding.BookItemBinding

class BookCreator(diff: DiffUtil.ItemCallback<Book>)
    : PagedItemCreator<Book, BookItemBinding>(2, diff){
    override fun onBindItemView(binding: BookItemBinding, e: Book, inCreatorPosition: Int) {
        binding.book = e
        Log.d("BookCreator", e.toString())
    }



    override fun onCreateItemBinding(parent: ViewGroup, viewType: Int): BookItemBinding {
        val inflater = LayoutInflater.from(parent.context)
        return BookItemBinding.inflate(inflater, parent, false)
    }

}