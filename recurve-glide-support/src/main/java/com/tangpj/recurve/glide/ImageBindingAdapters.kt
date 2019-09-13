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
package com.tangpj.recurve.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.CropSquareTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


/**
 * Created by tang on 2018/3/7.
 * Binding adapters that work with a fragment instance.
 */
@BindingAdapter(value = [
    "imageUrl",
    "shape",
    "radius",
    "radiusMargin",
    "placeHolderSrc", "fallbackSrc", "errorSrc"],requireAll = false)
fun ImageView.bindImage(
        url: String?,
        shape: String?,
        radius: Int,
        radiusMargin: Int,
        placeholderRes: Drawable?,
        fallbackRes: Drawable?,
        errorRes: Drawable?) {
    Glide.with(this).load(url).apply {
        apply(RequestOptions().apply {
            placeholder(placeholderRes)
            fallback(fallbackRes)
            error(errorRes)
          
        })
        if (radius > 0){
            apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(radius,radiusMargin)))
        }
        if(shape == Shape.SQUARE.value){
            apply(RequestOptions.bitmapTransform(CropSquareTransformation()))
        }
    }.into(this)
}

enum class Shape(val value: String){
    CIRCLE("circle"),
    SQUARE("square"),
    Normal("normal")
}


