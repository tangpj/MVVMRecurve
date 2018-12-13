package com.tangpj.recurve.ui.creator.ext

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.google.android.material.appbar.CollapsingToolbarLayout

class CollapsingToolbarLayoutExt(val context: Context){

    var contentScrim: Drawable? = null

    var toolbarExt: ToolbarExt? = null

    var collapsingCreator
            : ((inflater: LayoutInflater, CollapsingToolbarLayout) -> View)? = null


    fun contentScrimColor(@ColorInt colorId: Int){
        contentScrim = ColorDrawable(colorId)
    }

    fun contentScrimDrawable(@DrawableRes drawableId: Int){
        contentScrim = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getDrawable(drawableId)
        }else{
            context.resources.getDrawable(drawableId)
        }
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