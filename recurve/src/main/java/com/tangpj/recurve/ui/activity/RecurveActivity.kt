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

abstract class RecurveActivity : AppCompatActivity(), AppbarCreator {

    private lateinit var appbarCreator: AppbarCreator

    private lateinit var activityRecurveBinding: ActivityRecurveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecurveBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_recurve)
        appbarCreator = RecurveAppbarCreator(activityRecurveBinding)

    }


    override fun creatorToolbar(title: String?, collapsingCreator: ((
            inflater: LayoutInflater,
            CollapsingToolbarLayout,
            Toolbar) -> View)?) {

        appbarCreator.creatorToolbar(title, collapsingCreator)
    }
}