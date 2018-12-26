package com.tangpj.recurve.ui.creator.ext

import android.content.Context

class AppbarExt(val context: Context){

    var scrollEnable: Boolean = true

    var scrollFlags: String? = null

    var toolbarExt: ToolbarExt? = null

    var collapsingToolbarExt: CollapsingToolbarLayoutExt? = null

    fun scrollEnable(scrollEnable: Boolean){
        this.scrollEnable = scrollEnable
    }

    fun scrollFlags(scrollFlags: String){
        this.scrollFlags = scrollFlags
    }

    fun toolBar(init: ToolbarExt.() -> Unit){
        val toolbarExt = ToolbarExt()
        toolbarExt.init()
        this.toolbarExt = toolbarExt
    }

    fun collapsingToolbar(init: CollapsingToolbarLayoutExt.() -> Unit){
        val collapsingToolbarLayoutExt = CollapsingToolbarLayoutExt()
        collapsingToolbarLayoutExt.init()
        this.collapsingToolbarExt = collapsingToolbarLayoutExt
    }

}