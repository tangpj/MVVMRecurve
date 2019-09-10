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
import android.annotation.SuppressLint
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.CropSquareTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * Created by tang on 2018/3/8.
 */

@SuppressLint("CheckResult")
@JvmOverloads fun RequestOptions
        .default(placeholderRes: Int = 0,
                 fallbackRes: Int  = 0,
                 errorRes: Int = 0,
                 isCircle: Boolean = false,
                 radius: Int = 0,
                 radiusMargin: Int = 0
                 ): RequestOptions{

    if (isCircle){
        this.circleCrop()
    }
    if (placeholderRes != 0){
        placeholder(placeholderRes)
    }
    if (fallbackRes != 0){
        fallback(fallbackRes)
    }
    if (errorRes != 0){
        error(errorRes)
    }
    if (!isCircle && radius > 0){
        return RequestOptions.bitmapTransform(RoundedCornersTransformation(radius,radiusMargin))
    }
    return this
}

@SuppressLint("CheckResult")
@JvmOverloads fun RequestOptions
        .square( placeholderRes: Int = 0
                , fallbackRes: Int = 0
                , errorRes: Int = 0): RequestOptions{

    if (placeholderRes != 0){
        placeholder(placeholderRes)
    }
    if (fallbackRes != 0){
        fallback(fallbackRes)
    }
    if (errorRes != 0){
        error(errorRes)
    }
    return RequestOptions.bitmapTransform(CropSquareTransformation())
}


