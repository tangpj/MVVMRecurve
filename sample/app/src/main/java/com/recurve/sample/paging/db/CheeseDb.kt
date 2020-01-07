package com.recurve.sample.paging.db
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


import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.*
import android.content.Context
import android.util.Log
import com.recurve.coroutines.io
import com.recurve.mvvmrecurve.paging.Book
import com.recurve.mvvmrecurve.paging.Cheese

/**
 * Singleton database object. Note that for a real app, you should probably use a Dependency
 * Injection framework or Service Locator to create the singleton database.
 */
@Database(entities = arrayOf(Cheese::class, Book::class), version = 1)
abstract class CheeseDb : RoomDatabase() {
    abstract fun cheeseDao(): CheeseDao

    abstract fun bookDao(): BookDao

    companion object {
        private var instance: CheeseDb? = null
        const val TAG = "CheeseDb"

        @Synchronized
        fun get(context: Context): CheeseDb {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        CheeseDb::class.java, "CheeseDatabase")
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                fillInDb(context.applicationContext)
                            }
                        }).build()
            }
            return instance!!
        }

        /**
         * fill database with list of cheeses
         */
        private fun fillInDb(context: Context) {
            // inserts in Room are executed on the current thread, so we insert in the background
            Log.d(TAG, "insert start")

            io {
                Log.d(TAG, "insert cheese start")
                get(context).cheeseDao().insert(
                        CHEESE_DATA.map { Cheese(id = 0, name = it) })
                CHEESE_DATA.size
            }

            val insertBookResult = io {
                Log.d(TAG, "insert book start")
                get(context).bookDao().insert(
                        BOOK_DATA.map { Book(id = 0, name = it) })
                //test code, callback insert size
                BOOK_DATA.size
            }
            insertBookResult{
                Log.d(TAG, "insert book success, size = $it")
            }
        }
    }
}


private val CHEESE_DATA = arrayListOf(
        "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
        "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
        "Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese"
        )

private val BOOK_DATA = arrayListOf("Kotlin", "Java", "Scala", "C#","C", "C++",
        "JS", "RN", "Flutter", "Dart", "go", "sql", "database","swift", "my sql", "sql server",
        "groovy","gradle", "xml", "http", "http1", "http2", "http3", "tcp", "udp", "ip")
