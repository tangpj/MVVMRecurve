package com.tangpj.recurve.ui.creator

import androidx.recyclerview.widget.RecyclerView
import com.tangpj.recurve.recyclerview.creator.Creator


/**
 * 列表布局创建器
 *
 * @className: ListViewCreator
 * @author: tpj
 * @createTime: 2019/1/17 20:46
 */
interface RecyclerViewInit{

    /**
     * 添加内容创建器
     *
     * @method: addItemCreator
     * @author: tpj
     * @createTime: 2019/1/17 21:07
     * @param creator Item内容创建器
     */
    fun addItemCreator(creator: Creator)

    /**
     * 添加内容创建器到指定位置
     *
     * @method: addItemCreator
     * @author: tpj
     * @createTime: 2019/1/17 21:08
     */
    fun addItemCreator(index: Int, creator: Creator)

    fun setLayoutManager(lm: RecyclerView.LayoutManager)


}