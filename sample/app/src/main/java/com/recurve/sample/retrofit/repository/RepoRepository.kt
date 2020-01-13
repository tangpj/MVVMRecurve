package com.recurve.sample.retrofit.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.recurve.core.resource.NetworkBoundResource
import com.recurve.core.resource.Resource
import com.recurve.sample.retrofit.api.GithubService
import com.recurve.sample.retrofit.api.RepoSearchResponse
import com.recurve.sample.retrofit.db.GithubDb
import com.recurve.sample.retrofit.db.RepoDao
import com.recurve.sample.retrofit.vo.Repo
import com.recurve.sample.retrofit.vo.RepoSearchResult
import com.recurve.sample.util.AbsentLiveData

class RepoRepository constructor(
        private val db: GithubDb,
        private val repoDao: RepoDao,
        private val githubService: GithubService
) {


    fun search(query: String): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, RepoSearchResponse>() {

            override fun saveCallResult(item: RepoSearchResponse) {
                val repoIds = item.items.map { it.id }
                val repoSearchResult = RepoSearchResult(
                        query = query,
                        repoIds = repoIds,
                        totalCount = item.total,
                        next = item.nextPage
                )
                db.runInTransaction {
                    repoDao.insertRepos(item.items)
                    repoDao.insert(repoSearchResult)
                }
            }

            override fun shouldFetch(data: List<Repo>?) = data == null

            override fun loadFromDb(): LiveData<List<Repo>> {
                return Transformations.switchMap(repoDao.search(query)) { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        repoDao.loadOrdered(searchData.repoIds)
                    }
                }
            }

            override fun createCall() = githubService.searchRepos(query)

        }.asLiveData()
    }
}
