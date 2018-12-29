package com.tangpj.recurve.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.tangpj.recurve.databinding.ActivityRecurveBinding
import com.tangpj.recurve.databinding.ToolbarCollapsingRecurveBinding
import com.tangpj.recurve.databinding.ToolbarRecurveBinding
import com.tangpj.recurve.ui.creator.ext.AppbarExt
import com.tangpj.recurve.ui.creator.ext.CollapsingToolbarLayoutExt
import com.tangpj.recurve.ui.creator.ext.ToolbarExt

class RecurveAppbarCreator(
        private val activity: AppCompatActivity,
        private var activityRecurveBinding: ActivityRecurveBinding): AppbarCreator {

    override fun appbar(init: AppbarExt.() -> Unit): AppBarLayout {

        val context = activityRecurveBinding.appbarLayout.context
        val appbarExt = AppbarExt(context)
        init.invoke(appbarExt)
        val inflater = LayoutInflater.from(context)

        val toolbarBinding = appbarExt.toolbarExt?.let {
            createToolbar(it, inflater, activityRecurveBinding.appbarLayout )
        }
        if (toolbarBinding == null){
            appbarExt.collapsingToolbarExt?.let {
                createCollapsingToolbarLayout(it, inflater, activityRecurveBinding.appbarLayout)
            }
        }
        val scrollFlag = if(appbarExt.scrollEnable) {
            getScrollFlag(appbarExt)
        } else {
            0
        }
        val appbarChild = activityRecurveBinding.appbarLayout.getChildAt(0)
        val layoutParams = appbarChild?.let {
             it.layoutParams as? AppBarLayout.LayoutParams
        }
        layoutParams?.let {
            it.scrollFlags = scrollFlag
        }

        return activityRecurveBinding.appbarLayout
    }

    override fun createToolbar(
            toolbarExt: ToolbarExt,
            inflater: LayoutInflater,
            parent: ViewGroup): ToolbarRecurveBinding {

        val toolbarRecurveBinding = ToolbarRecurveBinding.inflate(inflater)
        val toolbar = toolbarRecurveBinding.toolbar
        toolbar.title = toolbarExt.title
        activityRecurveBinding.appbarLayout.addView(toolbar, 0)
        activity.setSupportActionBar(toolbar)
        return toolbarRecurveBinding
    }

    override fun createCollapsingToolbarLayout(
            collapsingToolbarLayoutExt: CollapsingToolbarLayoutExt,
            inflater: LayoutInflater,
            parent: ViewGroup)
            : ToolbarCollapsingRecurveBinding {

        val collapsingBinding = ToolbarCollapsingRecurveBinding.inflate(inflater)

        val collapsingCreator = collapsingToolbarLayoutExt.collapsingCreator

        val toolbar = collapsingBinding.toolbar
        collapsingToolbarLayoutExt.toolbarExt?.let {
            toolbar.title = it.title
        }
        if (collapsingToolbarLayoutExt.contentScrimColor != -1){
            collapsingBinding.collapsingToolbarLayout.setContentScrimColor(
                    activity.resources.getColor(collapsingToolbarLayoutExt.contentScrimColor))
        }
        activity.setSupportActionBar(toolbar)

        collapsingCreator?.let{

            val collapsingContent = collapsingCreator.invoke(
                    LayoutInflater.from(collapsingBinding.root.context),
                    collapsingBinding.collapsingToolbarLayout)
            collapsingBinding.collapsingToolbarLayout.addView(collapsingContent, 0)
            collapsingBinding.collapsingToolbarLayout
        }
        activityRecurveBinding.appbarLayout
                .addView(collapsingBinding.root)
        return collapsingBinding
    }


    private fun getScrollFlag(appbarExt: AppbarExt): Int {
        var result = 1
        appbarExt.scrollFlags?.let {
            val flags = it.trim().split("|")
            flags.forEach { flag ->
                result = result or when(flag){
                    "scroll" -> AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                    "exitUntilCollapsed" -> AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                    "enterAlways" -> AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                    "enterAlwaysCollapsed" -> AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED
                    "snap" -> AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
                    else -> {
                        0
                    }
                }
            }
        }

        return result

    }

}