package com.dicoding.core.domain.usecase

import com.dicoding.core.domain.model.Actrees
import com.dicoding.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {
    fun getNowPlayingMovies(region: String): Flow<Result<List<Movie>>>

    fun getTrendingMovies(region: String): Flow<Result<List<Movie>>>

    fun getUpcomingMovies(region: String): Flow<Result<List<Movie>>>

    fun getPopularMovies(region: String): Flow<Result<List<Movie>>>

    fun getRelatedMovies(id: Int): Flow<Result<List<Movie>>>

    fun getMoviesActrees(id: Int): Flow<Result<List<Actrees>>>

    fun getMoviesByQuery(query: String): Flow<Result<List<Movie>>>

    fun getAllFavoriteMovies(): Flow<List<Movie>>

    fun isFavoriteMovies(id: Int): Flow<Boolean>

    suspend fun saveMoviesAsFavorite(movie: Movie)

    suspend fun deleteMoviesFromFavorite(id: Int)
}