package com.dicoding.core.domain.usecase

import com.dicoding.core.domain.model.Actrees
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.domain.repository.InterfaceMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesInteractor @Inject constructor(private val movieRepository: InterfaceMovieRepository) :
    MoviesUseCase {

    override fun getNowPlayingMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getNowPlayingMovies(region)

    override fun getTrendingMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getTrendingMovies(region)

    override fun getUpcomingMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getUpcomingMovies(region)

    override fun getPopularMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getPopularMovies(region)

    override fun getRelatedMovies(id: Int): Flow<Result<List<Movie>>> =
        movieRepository.getRelatedMovies(id)

    override fun getMoviesActrees(id: Int): Flow<Result<List<Actrees>>> =
        movieRepository.getMoviesActrees(id)

    override fun getMoviesByQuery(query: String): Flow<Result<List<Movie>>> =
        movieRepository.getMoviesByQuery(query)

    override fun getAllFavoriteMovies(): Flow<List<Movie>> =
        movieRepository.getAllFavoriteMovies()

    override fun isFavoriteMovies(id: Int): Flow<Boolean> =
        movieRepository.isFavoriteMovies(id)

    override suspend fun saveMoviesAsFavorite(movie: Movie) =
        movieRepository.saveMoviesAsFavorite(movie)

    override suspend fun deleteMoviesFromFavorite(id: Int) =
        movieRepository.deleteMoviesFromFavorite(id)
}