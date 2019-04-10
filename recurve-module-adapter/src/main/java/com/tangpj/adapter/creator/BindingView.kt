package com.tangpj.adapter.creator

import androidx.databinding.ViewDataBinding

interface BindingView<E, Binding: ViewDataBinding>{
    fun onBindItemView(
            itemHolder: RecurveViewHolder<Binding>?,
            e: E?,
            inCreatorPosition: Int)
}