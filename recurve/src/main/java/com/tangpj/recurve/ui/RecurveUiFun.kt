package com.tangpj.recurve.ui

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.tangpj.recurve.ui.creator.AppbarCreator
import com.tangpj.recurve.ui.creator.ext.AppbarExt



fun AppCompatActivity.appbar(appbarCreator: AppbarCreator,
                             init: AppbarExt.() -> Unit): AppBarLayout
        = appbarCreator.appbar(init)

