package com.dicoding.core.data.remote.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.core.data.remote.local.entity.MoviesEntity


@Database(entities = [MoviesEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun movieDao(): MoviesDAO
}