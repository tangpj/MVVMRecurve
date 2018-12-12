package com.tangpj.recurve.ui.creator

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout

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
     * @method: creatorToolbar
     * @author: tangpengjian113
     * @createTime: 2018/12/5 21:21
     *
     * @param collapsingCreator 用于创建CollapsingToolbar，如果为空则创建普通ToolBar
     * @param title 页面标题
     */
    fun creatorToolbar(
            title: String? = null,
            collapsingCreator: ((inflater: LayoutInflater,
                                 CollapsingToolbarLayout,
                                 Toolbar) -> View)?
            = null)
}