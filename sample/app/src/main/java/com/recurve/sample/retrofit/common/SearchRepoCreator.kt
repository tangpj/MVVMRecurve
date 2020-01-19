package com.recurve.sample.retrofit.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.recurve.adapter.creator.ItemCreator
import com.recurve.sample.R
import com.recurve.sample.databinding.RepoItemBinding
import com.recurve.sample.databinding.RepoItemBindingImpl
import com.recurve.sample.retrofit.vo.Repo

class SearchRepoCreator : ItemCreator<Repo, RepoItemBinding>(){

    override fun onBindItemView(binding: RepoItemBinding, e: Repo, inCreatorPosition: Int) {
        binding.repo = e
    }

    override fun onCreateItemBinding(parent: ViewGroup, viewType: Int): RepoItemBinding {
        val inflate = LayoutInflater.from(parent.context)
        return DataBindingUtil.inflate(inflate, R.layout.repo_item, parent, false)
    }

}