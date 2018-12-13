package com.tangpj.recurve.ui.creator.ext

import android.content.Context

class AppbarExt(val context: Context){

    var layoutScrollFlags: Int = 0

    var toolbarExt: ToolbarExt? = null

    var collapsingToolbarExt: CollapsingToolbarLayoutExt? = null

    fun layoutScrollFlags(layoutScrollFlags: Int){
        this.layoutScrollFlags = layoutScrollFlags
    }

    fun toolBar(init: ToolbarExt.() -> Unit){
        val toolbarExt = ToolbarExt()
        toolbarExt.init()
        this.toolbarExt = toolbarExt
    }

    fun collapsingToolbar(init: CollapsingToolbarLayoutExt.() -> Unit){
        val collapsingToolbarLayoutExt = CollapsingToolbarLayoutExt(context)
        collapsingToolbarLayoutExt.init()
        this.collapsingToolbarExt = collapsingToolbarLayoutExt
    }

}