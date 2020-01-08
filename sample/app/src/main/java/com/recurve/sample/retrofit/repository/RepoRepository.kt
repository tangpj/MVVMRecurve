package com.recurve.sample.retrofit.repository

import com.recurve.sample.retrofit.api.GithubService
import com.recurve.sample.retrofit.db.GithubDb
import com.recurve.sample.retrofit.db.RepoDao

class RepoRepository constructor(
        private val db: GithubDb,
        private val repoDao: RepoDao,
        private val githubService: GithubService
) {


}
