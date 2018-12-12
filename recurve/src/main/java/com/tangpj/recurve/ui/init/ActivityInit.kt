package com.tangpj.recurve.ui.init

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

interface ActivityInit{

    fun <Binding : ViewDataBinding> initContentBinding(@LayoutRes layoutId: Int): Binding

}