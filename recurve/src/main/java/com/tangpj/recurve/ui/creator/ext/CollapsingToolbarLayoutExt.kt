package com.tangpj.recurve.ui.creator.ext

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.google.android.material.appbar.CollapsingToolbarLayout

class CollapsingToolbarLayoutExt{

    @DrawableRes
    var contentScrimDrawable: Int = -1

    var contentScrimColor: Int = -1

    var toolbarExt: ToolbarExt? = null

    var collapsingCreator
            : ((inflater: LayoutInflater, CollapsingToolbarLayout) -> View)? = null


    fun contentScrimColor(@ColorInt contentScrimColor: Int){
        this.contentScrimColor = contentScrimColor
    }

    fun contentScrimDrawable(@DrawableRes contentScrimDrawable: Int){
        this.contentScrimDrawable  = contentScrimDrawable
    }


    fun toolBar(init: ToolbarExt.() -> Unit){
        val toolbarExt = ToolbarExt()
        toolbarExt.init()
        this.toolbarExt = toolbarExt
    }

    fun collapsingContent(collapsingCreator
                         : ((inflater: LayoutInflater, CollapsingToolbarLayout) -> View)?){
        this.collapsingCreator = collapsingCreator
    }

}