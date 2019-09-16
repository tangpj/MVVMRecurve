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
package com.tangpj.recurve.ui.creator.ext

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.google.android.material.appbar.CollapsingToolbarLayout

class CollapsingToolbarLayoutExt{

    @DrawableRes
    var contentScrimDrawable: Int = -1

    @ColorRes
    var contentScrimColor: Int = -1

    @ColorInt
    var contentScrimColorInt: Int = -1

    var expandedTitleGravity: String? = ""

    var expandedTitleMargin = 0F
    var expandedTitleMarginStart = 0F
    var expandedTitleMarginTop = 0F
    var expandedTitleMarginEnd = 0F
    var expandedTitleMarginBottom = 0F

    var collapsingCreator
            : ((inflater: LayoutInflater, CollapsingToolbarLayout) -> View)? = null

    fun collapsingView(collapsingCreator
                         : ((inflater: LayoutInflater, CollapsingToolbarLayout) -> View)?){
        this.collapsingCreator = collapsingCreator
    }

    internal fun configExpandedTitleMargin(collapsingToolbarLayout: CollapsingToolbarLayout){
        when{
            expandedTitleMargin > 0 -> collapsingToolbarLayout.setExpandedTitleMargin(
                    expandedTitleMargin.toInt(),
                    expandedTitleMargin.toInt(),
                    expandedTitleMargin.toInt(),
                    expandedTitleMargin.toInt()
            )

            expandedTitleMarginStart > 0 ->
                collapsingToolbarLayout.expandedTitleMarginStart = expandedTitleMarginStart.toInt()

            expandedTitleMarginTop > 0 ->
                collapsingToolbarLayout.expandedTitleMarginTop = expandedTitleMarginTop.toInt()

            expandedTitleMarginEnd > 0 ->
                collapsingToolbarLayout.expandedTitleMarginEnd = expandedTitleMarginEnd.toInt()

            expandedTitleMarginBottom > 0 ->
                collapsingToolbarLayout.expandedTitleMarginBottom = expandedTitleMarginBottom.toInt()
        }
    }

}