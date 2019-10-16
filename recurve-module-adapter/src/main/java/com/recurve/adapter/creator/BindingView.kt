package com.recurve.adapter.creator

import androidx.databinding.ViewDataBinding

interface BindingView<E, Binding: ViewDataBinding>{
    fun onBindItemView(
            binding: Binding,
            e: E,
            inCreatorPosition: Int)
}