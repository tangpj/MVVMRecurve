/*
 * Copyright (C) 2018 Tang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tangpj.recurve.ui.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.tangpj.recurve.databinding.ActivityRecurveBinding
import com.tangpj.recurve.databinding.ToolbarCollapsingRecurveBinding
import com.tangpj.recurve.databinding.ToolbarRecurveBinding
import com.tangpj.recurve.ui.creator.ext.AppbarExt
import com.tangpj.recurve.ui.creator.ext.CollapsingToolbarLayoutExt

/**
 * Appbar创建器
 * 优先级：createCollapsingToolbarLayout > toolbar > title
 *
 * @className: RecurveAppbarCreator
 * @author: tpj
 * @createTime: 2019/1/25 21:14
 */
class RecurveAppbarCreator(
        private val activity: AppCompatActivity,
        private var activityRecurveBinding: ActivityRecurveBinding) : AppbarCreator {

    override fun appbar(init: AppbarExt.() -> Unit): AppBarLayout {

        val context = activityRecurveBinding.appbarLayout.context
        val appbarExt = AppbarExt(context)
        init.invoke(appbarExt)
        val inflater = LayoutInflater.from(context)

        val collapsingToolbarLayoutBinding = appbarExt.collapsingToolbarExt?.let {
            createCollapsingToolbarLayout(it, inflater, activityRecurveBinding.appbarLayout)
        }

        if (collapsingToolbarLayoutBinding == null) {
            createToolbar(appbarExt, inflater, activityRecurveBinding.appbarLayout)
        }
        val scrollFlag = if (appbarExt.scrollEnable) {
            getFlag(appbarExt.scrollFlags) {
                getScrollFlagInt(it)
            }
        } else {
            0
        }
        val appbarChild = activityRecurveBinding.appbarLayout.getChildAt(0)
        val layoutParams = appbarChild?.let {
            it.layoutParams as? AppBarLayout.LayoutParams
        }
        layoutParams?.let {
            it.scrollFlags = scrollFlag
        }

        return activityRecurveBinding.appbarLayout
    }

    override fun createToolbar(
            appbarExt: AppbarExt,
            inflater: LayoutInflater,
            parent: ViewGroup): ToolbarRecurveBinding {

        val toolbarRecurveBinding = ToolbarRecurveBinding.inflate(inflater)
        val toolbar = toolbarRecurveBinding.toolbar
        val toolbarExt = appbarExt.toolbarExt
        toolbar.title = if (toolbarExt != null) {
            toolbarExt.title
        } else {
            appbarExt.title
        }
        activityRecurveBinding.appbarLayout.addView(toolbar, 0)
        activity.setSupportActionBar(toolbar)
        return toolbarRecurveBinding
    }

    override fun createCollapsingToolbarLayout(
            collapsingToolbarLayoutExt: CollapsingToolbarLayoutExt,
            inflater: LayoutInflater,
            parent: ViewGroup)
            : ToolbarCollapsingRecurveBinding {

        val collapsingBinding = ToolbarCollapsingRecurveBinding.inflate(inflater)

        val collapsingCreator = collapsingToolbarLayoutExt.collapsingCreator

        val toolbar = collapsingBinding.toolbar
        collapsingToolbarLayoutExt.toolbarExt?.let {
            toolbar.title = it.title
        }
        if (collapsingToolbarLayoutExt.contentScrimColor != -1) {
            collapsingBinding.collapsingToolbarLayout.setContentScrimColor(
                    activity.resources.getColor(collapsingToolbarLayoutExt.contentScrimColor))
        }
        if (collapsingToolbarLayoutExt.contentScrimColorInt != -1) {
            collapsingBinding.collapsingToolbarLayout.setContentScrimColor(
                    collapsingToolbarLayoutExt.contentScrimColorInt)
        }
        collapsingBinding.collapsingToolbarLayout.expandedTitleGravity =
                getFlag(collapsingToolbarLayoutExt.expandedTitleGravity) {
                    getExpandedTitleGravityFlag(it) }

        activity.setSupportActionBar(toolbar)

        collapsingCreator?.let {

            val collapsingContent = collapsingCreator.invoke(
                    LayoutInflater.from(collapsingBinding.root.context),
                    collapsingBinding.collapsingToolbarLayout)
            collapsingBinding.collapsingToolbarLayout.addView(collapsingContent, 0)
            collapsingBinding.collapsingToolbarLayout
        }
        activityRecurveBinding.appbarLayout
                .addView(collapsingBinding.root)
        return collapsingBinding
    }


    private fun getExpandedTitleGravityFlag(flagStr: String) =
            when (flagStr) {
                "top" -> 0x30
                "bottom" -> 0x50
                "left" -> 0x03
                "right" -> 0x05
                "center_vertical" -> 0x10
                "fill_vertical" -> 0x70
                "center_horizontal" -> 0x01
                "center" -> 0x11
                "start" -> 0x00800003
                "end" -> 0x00800005
                else -> 0
            }

    private fun getScrollFlagInt(flagStr: String): Int {
        return when (flagStr) {
            "scroll" -> AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
            "exitUntilCollapsed" -> AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
            "enterAlways" -> AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            "enterAlwaysCollapsed" -> AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED
            "snap" -> AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
            else -> {
                0
            }
        }
    }


    private fun getFlag(flagsStr: String?, fragTransformInt: (String) -> Int): Int {
        flagsStr ?: return 0
        var result: Int
        val flagList = flagsStr.trim().split('|')
        result = flagList
                .map { fragTransformInt.invoke(it) }
                .reduce { resultFlag, flag -> resultFlag or flag }

        return result
    }
}