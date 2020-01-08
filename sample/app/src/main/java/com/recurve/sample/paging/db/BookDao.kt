package com.recurve.sample.paging.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.recurve.sample.paging.vo.Book

@Dao
interface BookDao{

    @Query("SELECT * FROM Book ORDER BY name COLLATE NOCASE ASC")
    fun allBookByName(): DataSource.Factory<Int, Book>

    @Insert
    fun insert(book: List<Book>)

    @Insert
    fun insert(book: Book)

    @Delete
    fun delete(book: Book)
}