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
 * @author: tpj
 * @createTime: 2019/1/16 15:09
 */
fun AppCompatActivity.appbar(appbarCreator: AppbarCreator,
                             init: AppbarExt.() -> Unit): AppBarLayout
        = appbarCreator.appbar(init)


fun Fragment.loadingStrategy(){

}