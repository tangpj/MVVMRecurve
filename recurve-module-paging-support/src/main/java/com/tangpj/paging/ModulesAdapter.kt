package com.tangpj.paging

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.tangpj.adapter.adapter.ModulesAdapter
import com.tangpj.adapter.creator.ItemCreator

fun <E, Binding: ViewDataBinding>
        ModulesAdapter.addPagedCreator(
        itemCreator: ItemCreator<E, Binding>,
        diffCallback: DiffUtil.ItemCallback<E>) : PagedListItemCreator<E, Binding> {
    val pagedCreator = itemCreator.toPagedCreator(diffCallback)
    addCreator(pagedCreator)
    return pagedCreator
}

fun <E, Binding: ViewDataBinding>
        ModulesAdapter.addPagedCreator(
        itemCreator: ItemCreator<E, Binding>,
        config: AsyncDifferConfig<E>) : PagedListItemCreator<E, Binding> {
    val pagedCreator = itemCreator.toPagedCreator(config)
    addCreator(pagedCreator)
    return pagedCreator
}