package com.tangpj.recurve.ui.acitivty

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tangpj.recurve.R
import com.tangpj.recurve.databinding.ActivityRecurveBinding
import com.tangpj.recurve.ui.appbar
import com.tangpj.recurve.ui.creator.AppbarCreator
import com.tangpj.recurve.ui.creator.ContentCreate
import com.tangpj.recurve.ui.creator.RecurveAppbarCreator
import com.tangpj.recurve.ui.creator.RecurveContentCreate
import com.tangpj.recurve.ui.creator.ext.AppbarExt

abstract class RecurveActivity:
        AppCompatActivity(), ContentCreate {

    private lateinit var activityRecurveBinding: ActivityRecurveBinding

    private lateinit var appbarCreator: AppbarCreator
    private lateinit var contentCreate: ContentCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecurveBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_recurve)
        contentCreate = RecurveContentCreate(activityRecurveBinding)
        appbarCreator = RecurveAppbarCreator(activityRecurveBinding)
    }

    override fun <Binding : ViewDataBinding> initContentBinding(@LayoutRes layoutId: Int): Binding
            = contentCreate.initContentBinding(layoutId)

    fun appbar(init: AppbarExt.() -> Unit){
        appbar(appbarCreator, init)
    }
}