package com.recurve.sample.paging.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(@PrimaryKey(autoGenerate = true) val id: Int, val name: String)