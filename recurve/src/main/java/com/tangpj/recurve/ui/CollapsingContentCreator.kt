package com.tangpj.recurve.ui

import android.view.View

/**
 * 创建折叠ActonBar内容
 *
 * @className: CollapsingContentCreator
 * @author: tangpj
 * @createTime: 2018/11/28 15:40
 */
interface CollapsingContentCreator{
    fun creatorCollapsingView(creator: (() -> View))
}