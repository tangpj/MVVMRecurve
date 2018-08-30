package tang.com.recurve.util

import android.content.Context
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt

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