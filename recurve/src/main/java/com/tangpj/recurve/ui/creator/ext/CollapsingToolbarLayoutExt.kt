package com.tangpj.recurve.ui.creator.ext

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.google.android.material.appbar.CollapsingToolbarLayout

class CollapsingToolbarLayoutExt{

    @DrawableRes
    var contentScrimDrawableRes: Int = -1

    var contentScrimColorRes: Int = -1

    var toolbarExt: ToolbarExt? = null

    var collapsingCreator
            : ((inflater: LayoutInflater, CollapsingToolbarLayout) -> View)? = null


    fun contentScrimColor(@ColorInt contentScrimColorRes: Int){
        this.contentScrimColorRes = contentScrimColorRes
    }

    fun contentScrimDrawable(@DrawableRes contentScrimDrawableRes: Int){
        this.contentScrimDrawableRes  = contentScrimDrawableRes
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