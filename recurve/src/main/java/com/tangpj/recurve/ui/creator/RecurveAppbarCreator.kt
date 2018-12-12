package com.tangpj.recurve.ui.creator

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.tangpj.recurve.databinding.ActivityRecurveBinding
import com.tangpj.recurve.databinding.ToolbarCollapsingRecurveBinding
import com.tangpj.recurve.databinding.ToolbarRecurveBinding

class RecurveAppbarCreator(val activityRecurveBinding: ActivityRecurveBinding) : AppbarCreator {
    override fun creatorToolbar(title: String?, collapsingCreator: ((
            inflater: LayoutInflater,
            CollapsingToolbarLayout,
            Toolbar) -> View)?) {

        val context = activityRecurveBinding.appBarLayout.context
        val inflater = LayoutInflater.from(context)
        val barLayout = if (collapsingCreator == null){
            val toolbarRecurveBinding = ToolbarRecurveBinding.inflate(inflater)
            title?.let { toolbarRecurveBinding.toolbar.title = it }
            toolbarRecurveBinding.root
        }else{
            val collapsingBinding = ToolbarCollapsingRecurveBinding.inflate(inflater)
            val collapsingContent = collapsingCreator.invoke(
                    LayoutInflater.from(collapsingBinding.root.context),
                    collapsingBinding.collapsingToolbarLayout,
                    collapsingBinding.toolbar)
            title?.let { collapsingBinding.toolbar.title = it }

            collapsingBinding.collapsingToolbarLayout.addView(collapsingContent)
            collapsingBinding.root
        }
        activityRecurveBinding.appBarLayout
                .addView(barLayout)
    }



}