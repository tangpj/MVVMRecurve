package com.tangpj.recurve.ui

import android.view.LayoutInflater
import android.view.View

/**
 * 创建折叠ActonBar内容
 *
 * @className: CollapsingContentCreator
 * @author: tangpj
 * @createTime: 2018/11/28 15:40
 */
interface AppbarCreator{


    /**
     * 初始化Toolbar
     *
     * @method:
     * @author: tangpengjian113
     * @createTime: 2018/12/5 21:21
     *
     * @param collapsingCreator 用于创建CollapsingToolbar，如果为空则创建普通ToolBar
     */
    fun creatorCollapsingView(collapsingCreator: ((LayoutInflater) -> View)? = null)
}