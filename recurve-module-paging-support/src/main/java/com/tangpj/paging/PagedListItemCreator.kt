package com.tangpj.paging

import androidx.databinding.ViewDataBinding
import androidx.paging.AsyncPagedListDiffer
import androidx.paging.PagedList
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.tangpj.adapter.creator.BindingView
import com.tangpj.adapter.creator.Creator
import com.tangpj.adapter.creator.DataOperator
import com.tangpj.adapter.creator.ItemCreator

class PagedListItemCreator<E, Binding : ViewDataBinding> private constructor(
        itemCreator: ItemCreator<E, Binding>):
        Creator by itemCreator,
        DataOperator<E> by itemCreator,
        BindingView<E, Binding> by itemCreator{

    private lateinit var mDiffer: AsyncPagedListDiffer<E>

    private val mListener =
            AsyncPagedListDiffer.PagedListListener<E>{ previousList, currentList ->
                mCurrentListChangeCallback?.invoke(previousList, currentList)
            }

    private var mCurrentListChangeCallback:
            ((previousList: PagedList<E>?, currentList: PagedList<E>?) -> Unit)? = null

    constructor(itemCreator: ItemCreator<E, Binding>, diffCallback:DiffUtil.ItemCallback<E>)
            : this(itemCreator) {
        mDiffer = AsyncPagedListDiffer<E>(itemCreator.adapter, diffCallback)
        mDiffer.addPagedListListener(mListener)
    }

    constructor( itemCreator: ItemCreator<E, Binding>, config: AsyncDifferConfig<E>)
            : this(itemCreator) {
        mDiffer = AsyncPagedListDiffer<E>(
                AdapterListUpdateCallback(itemCreator.adapter), config)
        mDiffer.addPagedListListener(mListener)
    }

    fun setCurrentListChangedCallback(
            currentListChangeCallback: (previousList: PagedList<E>?, currentList: PagedList<E>?)
            -> Unit){
        this.mCurrentListChangeCallback = currentListChangeCallback

    }

    fun submitList(pagedList: PagedList<E>, commitCallback: Runnable? = null){
        setDataList(pagedList)
        return mDiffer.submitList(pagedList, commitCallback)
    }

    override fun getItemCount(): Int = mDiffer.itemCount

    override fun getItem(position: Int): E? = mDiffer.getItem(position)

    fun getCurrentList() = mDiffer.currentList


}