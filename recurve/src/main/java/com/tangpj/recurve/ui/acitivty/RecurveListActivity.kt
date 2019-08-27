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
package com.tangpj.recurve.ui.acitivty

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tangpj.recurve.R
import com.tangpj.recurve.databinding.ActivityRecurveBinding
import com.tangpj.recurve.databinding.ContentListBinding
import com.tangpj.recurve.ui.appbar
import com.tangpj.recurve.ui.creator.*
import com.tangpj.recurve.ui.creator.ext.AppbarExt
import com.tangpj.recurve.ui.fragment.RecurveListFragment
import com.tangpj.adapter.creator.Creator

/**
 *
 * @className: RecurveListActivity
 * @author: tangpj
 * @createTime: 2019/1/18 14:44
 */
class RecurveListActivity :
        AppCompatActivity(), ContentCreate, RecyclerViewInit {


    private lateinit var activityRecurveBinding: ActivityRecurveBinding
    private lateinit var contentListBinding: ContentListBinding

    private lateinit var listFragment: RecurveListFragment

    private lateinit var appbarCreator: AppbarCreator
    private lateinit var contentCreate: ContentCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecurveBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_recurve)
        contentCreate = RecurveContentCreate(activityRecurveBinding)
        appbarCreator = RecurveAppbarCreator(this, activityRecurveBinding)
        initView()
    }

    override fun <Binding : ViewDataBinding> initContentBinding(@LayoutRes layoutId: Int): Binding
            = contentCreate.initContentBinding(layoutId)

    override fun addItemCreator(creator: Creator<*>) {
        listFragment.addItemCreator(creator)
    }

    override fun addItemCreator(index: Int, creator: Creator<*>) {
        listFragment.addItemCreator(index, creator)
    }

    override fun setLayoutManager(lm: RecyclerView.LayoutManager){
        listFragment.setLayoutManager(lm)
    }

    private fun initView(){
        contentListBinding = initContentBinding(R.layout.content_list)
        listFragment = RecurveListFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, listFragment).commit()
    }

    fun appbar(init: AppbarExt.() -> Unit) {
        appbar(appbarCreator, init)
    }

}