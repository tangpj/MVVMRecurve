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

import android.content.Context

class AppbarExt(val context: Context){

    var scrollEnable: Boolean = false

    var scrollFlags: String? = null

    var title: String? = null

    var toolbarExt: ToolbarExt? = null

    var collapsingToolbarExt: CollapsingToolbarLayoutExt? = null

    fun toolbar(init: ToolbarExt.() -> Unit){
        val toolbarExt = ToolbarExt()
        toolbarExt.init()
        this.toolbarExt = toolbarExt
    }

    fun collapsingToolbar(init: CollapsingToolbarLayoutExt.() -> Unit){
        val collapsingToolbarLayoutExt = CollapsingToolbarLayoutExt()
        collapsingToolbarLayoutExt.init()
        this.collapsingToolbarExt = collapsingToolbarLayoutExt
    }

}