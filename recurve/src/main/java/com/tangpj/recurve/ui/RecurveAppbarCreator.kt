package com.tangpj.recurve.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.tangpj.recurve.databinding.ActivityRecurveBinding
import com.tangpj.recurve.databinding.ToolbarCollapsingRecurveBinding
import com.tangpj.recurve.databinding.ToolbarRecurveBinding

class RecurveAppbarCreator(val activityRecurveBinding: ActivityRecurveBinding) : AppbarCreator{


    override fun creatorCollapsingView(collapsingCreator: ((LayoutInflater) -> View)?) {
        val context = activityRecurveBinding.appBarLayout.context
        val inflater = LayoutInflater.from(context)
        val toolbar = if (collapsingCreator == null){
            ToolbarRecurveBinding.inflate(inflater).root
        }else{
            val collapsingBinding = ToolbarCollapsingRecurveBinding.inflate(inflater)
            val collapsingContent =
                    collapsingCreator.invoke(LayoutInflater.from(collapsingBinding.root.context))

            collapsingBinding.collapsingToolbarLayout.addView(collapsingContent)
            collapsingBinding.root
        }
        collapsingCreator?.let {

        }
        activityRecurveBinding.appBarLayout
                .addView(toolbar)
    }


}