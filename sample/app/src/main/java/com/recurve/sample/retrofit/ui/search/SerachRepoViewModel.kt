package com.recurve.sample.retrofit.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.recurve.core.resource.Resource
import com.recurve.sample.retrofit.repository.RepoRepository
import com.recurve.sample.retrofit.vo.Repo
import com.recurve.sample.util.AbsentLiveData
import java.util.*

class SerachRepoViewModel constructor () : ViewModel(){

    var repoRepository: RepoRepository? = null

    private val _query = MutableLiveData<String>()
    val query : LiveData<String> = _query

    val results = Transformations
            .switchMap<String, Resource<List<Repo>>>(_query) { search ->
                if (search.isNullOrBlank()) {
                    AbsentLiveData.create()
                } else {
                    repoRepository?.search(search)
                }
            }

    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == _query.value) {
            return
        }
        _query.value = input
    }
}