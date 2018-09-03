package com.tangpj.recurve.util

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

class UiUtils{

    companion object {
        @ColorInt
        fun resolveColor(context: Context, @AttrRes styledAttributeId: Int): Int {
            val a = context.obtainStyledAttributes(intArrayOf(styledAttributeId))
            val color = a.getColor(0, 0)
            a.recycle()
            return color
        }
    }
}