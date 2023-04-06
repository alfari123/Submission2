package com.dicoding.core.data.remote.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.core.data.remote.local.entity.MoviesEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDAO {

    @Query("SELECT * FROM fav_movies")
    fun getAllFavoriteMovies(): Flow<List<MoviesEntity>>

    @Query("SELECT COUNT(id) FROM fav_movies WHERE id = :id")
    fun isFavoriteMovies(id: Int): Flow<Boolean>

    @Query("DELETE FROM fav_movies WHERE id = :id")
    suspend fun deleteMoviesFromFavorite(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMoviesAsFavorite(movieEntity: MoviesEntity)
}