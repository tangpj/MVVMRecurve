package com.recurve.sample.util

import android.app.Application
import androidx.room.Room
import com.recurve.retrofit2.LiveDataCallAdapterFactory
import com.recurve.sample.retrofit.api.GithubService
import com.recurve.sample.retrofit.db.GithubDb
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createDb(app: Application) : GithubDb {
    return Room.databaseBuilder(app, GithubDb::class.java, "github.db")
            .fallbackToDestructiveMigration()
            .build()
}

fun createGithubService() : GithubService {
    return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(GithubService::class.java)
}