package com.tangpj.recurve.ui.creator

import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tangpj.recurve.databinding.ActivityRecurveBinding

class RecurveContentCreate(var activityRecurveBinding: ActivityRecurveBinding) : ContentCreate {

    override fun <Binding : ViewDataBinding> initContentBinding(@LayoutRes layoutId: Int): Binding {
        val parent = activityRecurveBinding.content
        val inflater = LayoutInflater.from(parent.context)
        val binding: Binding = DataBindingUtil
                .inflate(inflater, layoutId, parent, false)

        parent.addView(binding.root)
        return binding

    }

}