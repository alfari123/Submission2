package com.dicoding.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.core.data.remote.local.room.AppDatabase
import com.dicoding.core.data.remote.local.room.MoviesDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MoviesDAO = appDatabase.movieDao()

//    @Provides
//    @Singleton
//    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
//        return Room.databaseBuilder(
//            context.applicationContext,
//            AppDatabase::class.java,
//            "movie_database"
//        ).build()
//    }


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("the_movie".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "movie_database.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}