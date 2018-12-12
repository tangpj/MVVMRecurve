package com.tangpj.recurve.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.tangpj.recurve.R
import com.tangpj.recurve.databinding.ActivityRecurveBinding
import com.tangpj.recurve.ui.creator.AppbarCreator
import com.tangpj.recurve.ui.creator.RecurveAppbarCreator
import com.tangpj.recurve.ui.init.ActivityInit
import com.tangpj.recurve.ui.init.RecurveActivityInit

abstract class RecurveActivity:
        AppCompatActivity(), AppbarCreator, ActivityInit{

    private lateinit var appbarCreator: AppbarCreator

    private lateinit var contentInit: ActivityInit

    private lateinit var activityRecurveBinding: ActivityRecurveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecurveBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_recurve)
        appbarCreator = RecurveAppbarCreator(activityRecurveBinding)
        contentInit = RecurveActivityInit(activityRecurveBinding)

    }

    override fun creatorToolbar(title: String?, collapsingCreator: ((
            inflater: LayoutInflater,
            CollapsingToolbarLayout,
            Toolbar) -> View)?)
            = appbarCreator.creatorToolbar(title, collapsingCreator)

    override fun <Binding : ViewDataBinding> initContentBinding(@LayoutRes layoutId: Int): Binding
            = contentInit.initContentBinding(layoutId)
}