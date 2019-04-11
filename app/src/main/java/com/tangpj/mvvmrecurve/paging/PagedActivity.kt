/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.tangpj.mvvmrecurve.paging

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import com.tangpj.adapter.adapter.ModulesAdapter
import com.tangpj.adapter.creator.RecurveViewHolder
import com.tangpj.mvvmrecurve.R
import com.tangpj.mvvmrecurve.databinding.CheeseItemBinding
import com.tangpj.paging.addPagedCreator
import kotlinx.android.synthetic.main.activity_paged.*
import timber.log.Timber

/**
 * Shows a list of Cheeses, with swipe-to-delete, and an input field at the top to add.
 * <p>
 * Cheeses are stored in a database, so swipes and additions edit the database directly, and the UI
 * is updated automatically using paging components.
 */
class PagedActivity : AppCompatActivity() {

    private val TAG = "PagedActivity"

    private val cheeseViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(CheeseViewModel::class.java)
    }

    private val bookViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(BookViewModel::class.java)
    }

    private val bookDiffCallback = object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem.id == newItem.id

        /**
         * Note that in kotlin, == checking on data classes compares all contents, but in Java,
         * typically you'll implement Object#equals, and use it to compare object contents.
         */
        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem == newItem
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Cheese>() {
        override fun areItemsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                oldItem.id == newItem.id

        /**
         * Note that in kotlin, == checking on data classes compares all contents, but in Java,
         * typically you'll implement Object#equals, and use it to compare object contents.
         */
        override fun areContentsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                oldItem == newItem
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paged)

        // Create adapter for the RecyclerView
        val adapter = ModulesAdapter()
        val creator =
                adapter.addPagedCreator(CheeseCreator(adapter), diffCallback)
        val bookCreator =
                adapter.addPagedCreator(BookCreator(adapter), bookDiffCallback)
        cheeseList.adapter = adapter

        cheeseViewModel.allCheeses.observe(this,Observer<PagedList<Cheese>> {
            Log.d( TAG,"load cheese size = ${it.size}")
            creator.submitList(it)
        })

        bookViewModel.allBooks.observe(this,Observer<PagedList<Book>> {
            Log.d( TAG, "load book size = ${it.size}")
            bookCreator.submitList(it)
        })


        // Subscribe the adapter to the ViewModel, so the items in the adapter are refreshed
        // when the list changes
//        viewModel.allCheeses.observe(this, Observer(adapter::submitList))

        initAddButtonListener()
        initSwipeToDelete()
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(recyclerView: RecyclerView,
                                          viewHolder: RecyclerView.ViewHolder): Int =
                    makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean = false

            // When an item is swiped, remove the item via the view model. The list item will be
            // automatically removed in response, because the adapter is observing the live list.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val binding = (viewHolder as? RecurveViewHolder<*>)?.binding
                (binding as? CheeseItemBinding)?.cheese?.let {
                    cheeseViewModel.remove(it)
                }
            }
        }).attachToRecyclerView(cheeseList)
    }

    private fun addCheese() {
        val newCheese = inputText.text.trim()
        if (newCheese.isNotEmpty()) {
            cheeseViewModel.insert(newCheese)
            inputText.setText("")
        }
    }

    private fun initAddButtonListener() {
        addButton.setOnClickListener {
            addCheese()
        }

        // when the user taps the "Done" button in the on screen keyboard, save the item.
        inputText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addCheese()
                return@setOnEditorActionListener true
            }
            false // action that isn't DONE occurred - ignore
        }
        // When the user clicks on the button, or presses enter, save the item.
        inputText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addCheese()
                return@setOnKeyListener true
            }
            false // event that isn't DOWN or ENTER occurred - ignore
        }
    }
}
