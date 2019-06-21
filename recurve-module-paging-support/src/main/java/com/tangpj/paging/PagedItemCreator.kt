package com.tangpj.paging

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.AsyncPagedListDiffer
import androidx.paging.PagedList
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.tangpj.adapter.adapter.ModulesAdapter
import com.tangpj.adapter.adapter.WRAP
import com.tangpj.adapter.creator.*

abstract class PagedItemCreator<E, Binding : ViewDataBinding>:
        Creator,
        BindingView<E, Binding>{

    private val mDiffer: AsyncPagedListDiffer<E>
    private val creatorType: Int

    private val mListener =
            AsyncPagedListDiffer.PagedListListener<E>{ previousList, currentList ->
                mCurrentListChangeCallback?.invoke(previousList, currentList)
            }

    private var mCurrentListChangeCallback:
            ((previousList: PagedList<E>?, currentList: PagedList<E>?) -> Unit)? = null

    private var itemClickListener: ((view: View, e: E, creatorPosition: Int) -> Unit)? = null


    constructor(aAdapter: ModulesAdapter, aCreatorType: Int = 0, aDiffCallback: DiffUtil.ItemCallback<E>) {
        creatorType = aCreatorType
        mDiffer = AsyncPagedListDiffer<E>(aAdapter, aDiffCallback)
        mDiffer.addPagedListListener(mListener)
    }

    constructor(adapter: ModulesAdapter, aCreatorType: Int = 0, config: AsyncDifferConfig<E>) {
        creatorType = aCreatorType
        mDiffer = AsyncPagedListDiffer<E>(
                AdapterListUpdateCallback(adapter), config)
        mDiffer.addPagedListListener(mListener)
    }

    fun setOnItemClickListener(listener: ((view: View, e: E, creatorPosition: Int) -> Unit)){
        this.itemClickListener = listener
    }

    override fun getItemCount(): Int = mDiffer.itemCount

    fun getItem(position: Int): E? = mDiffer.getItem(position)

    override fun getCreatorItemViewTypeByPosition(creatorPosition: Int): Int = creatorType

    override fun getCreatorItemViewTypeByViteType(viewType: Int): Int = creatorType

    override fun getCreatorType(): Int = creatorType
    override fun getSpan(): Int = WRAP

    @Suppress("UNCHECKED_CAST")
    override fun onBindItemView(itemHolder: RecurveViewHolder<*>, creatorPosition: Int) {
        if (getItemCount() == 0){
            return
        }
        val e: E? = getItem(creatorPosition)
        itemClickListener?.let { listener ->
            itemHolder.itemView.setOnClickListener {
                e ?: return@setOnClickListener
            listener.invoke(itemHolder.itemView, e , creatorPosition)
            }
        }

        onBindItemView(
                itemHolder as RecurveViewHolder<Binding>,
                e,
                creatorPosition)
    }


    fun setCurrentListChangedCallback(
            currentListChangeCallback: (previousList: PagedList<E>?, currentList: PagedList<E>?)
            -> Unit){
        this.mCurrentListChangeCallback = currentListChangeCallback

    }

    fun submitList(pagedList: PagedList<E>, commitCallback: Runnable? = null){
        return mDiffer.submitList(pagedList, commitCallback)
    }


    fun getCurrentList() = mDiffer.currentList


}