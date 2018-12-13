package com.tangpj.recurve.ui.creator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.tangpj.recurve.databinding.ActivityRecurveBinding
import com.tangpj.recurve.databinding.ToolbarCollapsingRecurveBinding
import com.tangpj.recurve.databinding.ToolbarRecurveBinding
import com.tangpj.recurve.ui.creator.ext.AppbarExt
import com.tangpj.recurve.ui.creator.ext.CollapsingToolbarLayoutExt
import com.tangpj.recurve.ui.creator.ext.ToolbarExt

class RecurveAppbarCreator(var activityRecurveBinding: ActivityRecurveBinding): AppbarCreator {

    override fun appbar(init: AppbarExt.() -> Unit): AppBarLayout {

        val context = activityRecurveBinding.appBarLayout.context
        val appbar = AppbarExt(context)
        init.invoke(appbar)
        val inflater = LayoutInflater.from(context)


        appbar.toolbarExt?.let {
            createToolbar(it, inflater, activityRecurveBinding.appBarLayout )
            return@appbar activityRecurveBinding.appBarLayout
        }

        appbar.collapsingToolbarExt?.let {
            createCollapsingToolbarLayout(it, inflater, activityRecurveBinding.appBarLayout)
        }

        return activityRecurveBinding.appBarLayout
    }

    override fun createToolbar(
            toolbarExt: ToolbarExt,
            inflater: LayoutInflater,
            parent: ViewGroup): Toolbar {

        val toolbarRecurveBinding = ToolbarRecurveBinding.inflate(inflater)
        val toolbar = toolbarRecurveBinding.toolbar
        toolbar.title = toolbarExt.title
        activityRecurveBinding.appBarLayout.addView(toolbar)
        return toolbar
    }

    override fun createCollapsingToolbarLayout(
            collapsingToolbarLayoutExt: CollapsingToolbarLayoutExt,
            inflater: LayoutInflater,
            parent: ViewGroup)
            : CollapsingToolbarLayout {

        val collapsingBinding = ToolbarCollapsingRecurveBinding.inflate(inflater)

        val collapsingCreator = collapsingToolbarLayoutExt.collapsingCreator

        val toolbar = collapsingBinding.toolbar
        collapsingToolbarLayoutExt.toolbarExt?.let {
            toolbar.title = it.title
        }

        val content = collapsingCreator?.let{

            val collapsingContent = collapsingCreator.invoke(
                    LayoutInflater.from(collapsingBinding.root.context),
                    collapsingBinding.collapsingToolbarLayout)
            collapsingBinding.collapsingToolbarLayout.addView(collapsingContent)
            collapsingBinding.collapsingToolbarLayout
        }
        activityRecurveBinding.appBarLayout
                .addView(collapsingBinding.root)
        return collapsingBinding.collapsingToolbarLayout
    }



}