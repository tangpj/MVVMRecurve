package com.tangpj.recurve.ui.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tangpj.recurve.R
import com.tangpj.recurve.databinding.ActivityRecurveBinding
import com.tangpj.recurve.ui.AppbarCreator

abstract class RecurveActivity : AppCompatActivity(), AppbarCreator {

    private lateinit var activityRecurveBinding: ActivityRecurveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecurveBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_recurve)

    }

    fun <T: ViewDataBinding> setRecurveContent(@LayoutRes layoutId: Int): T
            = DataBindingUtil.setContentView(this, layoutId)

    override fun creatorCollapsingView(creator: () -> View) {
        activityRecurveBinding.collapsingToolbarLayout.addView(creator.invoke())
    }

}