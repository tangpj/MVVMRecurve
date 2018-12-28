package com.tangpj.recurve.ui.creator.ext

import android.content.Context

class AppbarExt(val context: Context){

    var scrollEnable: Boolean = false

    var scrollFlags: String? = null

    var toolbarExt: ToolbarExt? = null

    var collapsingToolbarExt: CollapsingToolbarLayoutExt? = null

    fun toolbar(init: ToolbarExt.() -> Unit){
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