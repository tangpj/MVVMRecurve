package com.tangpj.recurve.ui.creator

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

/**
 * 内容创建器
 * 
 * @className: ContentCreate
 * @author: tpj
 * @createTime: 2019/1/17 20:53
 */
interface ContentCreate{

    fun <Binding : ViewDataBinding> initContentBinding(@LayoutRes layoutId: Int): Binding

}