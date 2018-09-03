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
package com.tangpj.recurve.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.tangpj.recurve.util.circle
import com.tangpj.recurve.util.default
import com.tangpj.recurve.util.rounded
import com.tangpj.recurve.util.square


/**
 * Created by tang on 2018/3/7.
 * Binding adapters that work with a fragment instance.
 */
class ImageBindingAdapters constructor(private val requestManager: RequestManager
                                                             , private val placeholderRes: Int = 0
                                                             , private val fallbackRes: Int = 0
                                                             , private val errorRes: Int = 0) {

    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String) {
        requestManager.load(url)
                .apply(RequestOptions().default(placeholderRes,fallbackRes,errorRes))
                .into(imageView)
    }

    @BindingAdapter("imageCircle")
    fun bindImageCircle(imageView: ImageView, url: String){
        requestManager.load(url)
                .apply(RequestOptions().circle(placeholderRes,fallbackRes,errorRes))
                .into(imageView)
    }

    @BindingAdapter("imageSquare")
    fun bindImageSquare(imageView: ImageView, url: String){
        requestManager.load(url)
                .apply(RequestOptions().square(placeholderRes,fallbackRes,errorRes))
                .into(imageView)
    }

    @BindingAdapter("imageRounded")
    fun bindImageRounded(imageView: ImageView, url: String){
        requestManager.load(url)
                .apply(RequestOptions().rounded(placeholderRes,fallbackRes,errorRes))
                .into(imageView)
    }

}
