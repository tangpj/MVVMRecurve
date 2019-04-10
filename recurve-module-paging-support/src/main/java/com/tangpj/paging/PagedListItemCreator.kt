package com.tangpj.paging

import androidx.databinding.ViewDataBinding
import com.tangpj.adapter.creator.Creator
import com.tangpj.adapter.creator.DataOperator
import com.tangpj.adapter.creator.ItemCreator

class PagedListItemCreator<E, Binding : ViewDataBinding>(
        private val itemCreator: ItemCreator<E, Binding>)
    : Creator by itemCreator, DataOperator<E> by itemCreator{


}