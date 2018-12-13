package com.tangpj.recurve.ui.creator

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

interface ContentCreate{

    fun <Binding : ViewDataBinding> initContentBinding(@LayoutRes layoutId: Int): Binding

}