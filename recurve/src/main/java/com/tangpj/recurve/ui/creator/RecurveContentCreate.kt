package com.tangpj.recurve.ui.creator

import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.appbar.AppBarLayout
import com.tangpj.recurve.databinding.ActivityRecurveBinding

class RecurveContentCreate(var activityRecurveBinding: ActivityRecurveBinding) : ContentCreate {

    override fun <Binding : ViewDataBinding> initContentBinding(@LayoutRes layoutId: Int): Binding {

        val inflater = LayoutInflater.from(activityRecurveBinding.clRoot.context)
        val binding: Binding = DataBindingUtil
                .inflate(inflater, layoutId, activityRecurveBinding.clRoot, false)
        val contentLayoutParams = binding.root.layoutParams
                as? CoordinatorLayout.LayoutParams
        if (contentLayoutParams?.behavior == null){
            contentLayoutParams?.behavior = AppBarLayout.ScrollingViewBehavior()
        }
        activityRecurveBinding.clRoot.addView(binding.root, contentLayoutParams)
        return binding

    }

}