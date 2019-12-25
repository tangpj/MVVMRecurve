package com.recurve.paging

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.paging.AsyncPagedListDiffer
import androidx.paging.PagedList
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.recurve.adapter.ModulesAdapter
import com.recurve.adapter.WRAP
import com.recurve.adapter.creator.*
import java.lang.NullPointerException

abstract class PagedItemCreator<E, Binding : ViewDataBinding>:
        Creator<Binding>,
        BindingView<E, Binding>{

    private lateinit var  mDiffer: AsyncPagedListDiffer<E>
    private val creatorType: Int
    private val diffConfig: AsyncDifferConfig<E>?
    private val diffCallback: DiffUtil.ItemCallback<E>?

    protected lateinit var mAdapter: ModulesAdapter

    private val mListener =
            AsyncPagedListDiffer.PagedListListener<E>{ previousList, currentList ->
                mCurrentListChangeCallback?.invoke(previousList, currentList)
            }

    private var mCurrentListChangeCallback:
            ((previousList: PagedList<E>?, currentList: PagedList<E>?) -> Unit)? = null

    private var itemClickListener: ((view: View, e: E, creatorPosition: Int) -> Unit)? = null


    constructor(aCreatorType: Int = 0, aDiffCallback: DiffUtil.ItemCallback<E>) {
        creatorType = aCreatorType
        diffCallback = aDiffCallback
        diffConfig = null
    }

    constructor(aCreatorType: Int = 0, config: AsyncDifferConfig<E>) {
        creatorType = aCreatorType
        diffConfig = config
        diffCallback = null
    }

    override fun onBindCreator(adapter: ModulesAdapter) {
        mAdapter = adapter
        if (diffConfig != null){
            mDiffer = AsyncPagedListDiffer<E>(
                    AdapterListUpdateCallback(mAdapter), diffConfig)
        }else if (diffCallback != null){
            mDiffer = AsyncPagedListDiffer<E>(mAdapter, diffCallback)
        }
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
        val e: E = getItem(creatorPosition) ?: throw NullPointerException("node is null")
        itemClickListener?.let { listener ->
            itemHolder.itemView.setOnClickListener {
                e ?: return@setOnClickListener
            listener.invoke(itemHolder.itemView, e , creatorPosition)
            }
        }

        onBindItemView(
                itemHolder.binding as Binding,
                e,
                creatorPosition)
    }

    fun getPageList() = mDiffer.currentList?.toList()

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