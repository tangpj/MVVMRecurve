package com.recurve.sample.retrofit.ui.search

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.recurve.core.ui.fragment.RecurveListFragment
import com.recurve.sample.R
import com.recurve.sample.databinding.FragmentSearchRepoBinding
import com.recurve.sample.retrofit.common.SearchRepoCreator
import com.recurve.sample.retrofit.repository.RepoRepository
import com.recurve.sample.util.createDb
import com.recurve.sample.util.createGithubService

class SearchRepoFragment : RecurveListFragment(){

    private lateinit var binding: FragmentSearchRepoBinding
    private val searchViewModel by lazy { ViewModelProviders.of(this).get(SerachRepoViewModel::class.java)}
    private lateinit var creator: SearchRepoCreator

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_search_repo, container, false)

        initViewRecyclerView(binding.repoList)
        creator = SearchRepoCreator()
        addItemCreator(creator)
        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.query = searchViewModel.query
        initSearchInputListener()
        initRepository()

        binding.searchResult = searchViewModel.results
        searchViewModel.results.observe(viewLifecycleOwner, Observer { result ->
            result?.data?.let {
                creator.setDataList(it)
            }
        })

    }

    private fun initRepository(){
        activity?.application?.let {
            //recommended di mode injection
            val db = createDb(it)
            val service = createGithubService()
            val repository = RepoRepository(db,service)
            searchViewModel.repoRepository = repository
        }
    }

    private fun initSearchInputListener() {
        binding.input.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }
        binding.input.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch(view)
                true
            } else {
                false
            }
        }
    }

    private fun doSearch(v: View) {
        val query = binding.input.text.toString()
        // Dismiss keyboard
        dismissKeyboard(v.windowToken)
        searchViewModel.setQuery(query)
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}