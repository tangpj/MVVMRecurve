package com.tangpj.mvvmrecurve.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tangpj.adapter.adapter.ModulesAdapter
import com.tangpj.adapter.creator.ItemCreator
import com.tangpj.adapter.creator.RecurveViewHolder
import com.tangpj.mvvmrecurve.databinding.BookItemBinding

class BookCreator(adapter: ModulesAdapter) : ItemCreator<Book, BookItemBinding>(adapter,2){

    override fun onBindItemView(itemHolder: RecurveViewHolder<BookItemBinding>, e: Book?, inCreatorPosition: Int) {
        e ?: return
        itemHolder.let {
            it.binding.book = e
        }
        Log.d("BookCreator", e.toString())
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecurveViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        return RecurveViewHolder(BookItemBinding.inflate(inflater, parent, false))
    }

}