package com.tangpj.recurve.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.tangpj.recurve.ui.creator.AppbarCreator
import com.tangpj.recurve.ui.creator.ext.AppbarExt

/**
 * 创建appbar
 *
 * @method: appbar
 * @author: tangpengjian113
 * @createTime: 2019/1/16 15:09
 */
fun AppCompatActivity.appbar(appbarCreator: AppbarCreator,
                             init: AppbarExt.() -> Unit): AppBarLayout
        = appbarCreator.appbar(init)


fun Fragment.loadingStrategy(){

}