package com.tangpj.recurve.ui.init

import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tangpj.recurve.databinding.ActivityRecurveBinding

class RecurveActivityInit(
        private val activityRecurveBinding: ActivityRecurveBinding) : ActivityInit{

    override fun <Binding : ViewDataBinding> initContentBinding(@LayoutRes layoutId: Int): Binding {
        val inflater = LayoutInflater.from(activityRecurveBinding.content.context)
        val binding: Binding = DataBindingUtil
                .inflate(inflater, layoutId, activityRecurveBinding.content, false)
        activityRecurveBinding.content.addView(binding.root)
        return binding

    }

}