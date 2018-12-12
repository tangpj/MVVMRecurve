package com.tangpj.recurve.dagger2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tangpj.recurve.R
import com.tangpj.recurve.databinding.ActivityRecurveBinding
import com.tangpj.recurve.databinding.ToolbarRecurveBinding
import com.tangpj.recurve.ui.creator.AppbarCreator
import dagger.android.support.DaggerAppCompatActivity

abstract class DaggerRecurveActivity: DaggerAppCompatActivity(), AppbarCreator {

    private lateinit var activityRecurveBinding: ActivityRecurveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecurveBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_recurve)

    }

    fun <T: ViewDataBinding> setRecurveContent(@LayoutRes layoutId: Int): T
            = DataBindingUtil.setContentView(this, layoutId)




    override fun creatorToolbar(collapsingCreator: ((LayoutInflater) -> View)?) {
        val inflater = LayoutInflater.from(activityRecurveBinding.appBarLayout.context)
        val toolbarBinding = ToolbarRecurveBinding.inflate(inflater)
        activityRecurveBinding.appBarLayout.addView(toolbarBinding.root)
        collapsingCreator?.let {
            it.invoke(inflater)

        }
    }

}