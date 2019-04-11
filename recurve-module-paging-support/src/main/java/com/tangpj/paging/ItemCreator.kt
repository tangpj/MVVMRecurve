package com.tangpj.paging

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.tangpj.adapter.creator.ItemCreator

fun <E, Binding: ViewDataBinding>ItemCreator<E, Binding>
        .toPagedCreator(diffCallback: DiffUtil.ItemCallback<E>) : PagedListItemCreator<E, Binding>
        = PagedListItemCreator(this, diffCallback)

fun <E, Binding: ViewDataBinding>ItemCreator<E, Binding>
        .toPagedCreator(config: AsyncDifferConfig<E>) : PagedListItemCreator<E, Binding>
        = PagedListItemCreator(this, config)