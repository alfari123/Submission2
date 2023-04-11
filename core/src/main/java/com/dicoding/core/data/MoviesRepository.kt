package com.dicoding.core.data

import com.dicoding.core.data.apis.ApiService
import com.dicoding.core.data.remote.local.room.MoviesDAO
import com.dicoding.core.domain.model.Actrees
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.domain.repository.InterfaceMovieRepository
import com.dicoding.core.utils.mapToCast
import com.dicoding.core.utils.mapToMovie
import com.dicoding.core.utils.mapToMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MoviesDAO
) : InterfaceMovieRepository {

    override fun getNowPlayingMovies(region: String): Flow<Result<List<Movie>>> = flow {
        val response = apiService.getNowPlayingMovies(region).results

        val movies = mutableListOf<Movie>()
        response?.forEach { movieResponse ->
            movies.add(movieResponse.mapToMovie())
        }

        emit(Result.success(movies))
    }.catch { e ->
        emit(Result.failure(e))
    }

    override fun getTrendingMovies(region: String): Flow<Result<List<Movie>>> = flow {
        val response = apiService.getTrendingMovies(region).results

        val movies = mutableListOf<Movie>()
        response?.forEach { movieResponse ->
            movies.add(movieResponse.mapToMovie())
        }

        emit(Result.success(movies))
    }.catch { e ->
        emit(Result.failure(e))
    }

    override fun getUpcomingMovies(region: String): Flow<Result<List<Movie>>> = flow {
        val response = apiService.getUpcomingMovies(region).results

        val movies = mutableListOf<Movie>()
        response?.forEach { movieResponse ->
            movies.add(movieResponse.mapToMovie())
        }

        emit(Result.success(movies))
    }.catch { e ->
        emit(Result.failure(e))
    }

    override fun getPopularMovies(region: String): Flow<Result<List<Movie>>> = flow {
        val response = apiService.getPopularMovies(region).results

        val movies = mutableListOf<Movie>()
        response?.forEach { movieResponse ->
            movies.add(movieResponse.mapToMovie())
        }

        emit(Result.success(movies))
    }.catch { e ->
        emit(Result.failure(e))
    }

    override fun getRelatedMovies(id: Int): Flow<Result<List<Movie>>> = flow {
        val response = apiService.getRelatedMovie(id).results
        val movies = mutableListOf<Movie>()
        response?.forEach { movieResponse ->
            movies.add(movieResponse.mapToMovie())
        }

        emit(Result.success(movies))
    }.catch { e ->
        emit(Result.failure(e))
    }

    override fun getMoviesActrees(id: Int): Flow<Result<List<Actrees>>> = flow {
        val response = apiService.getMovieCasts(id).cast
        val casts = mutableListOf<Actrees>()
        response.forEach { castResponse ->
            casts.add(castResponse.mapToCast())
        }
        emit(Result.success(casts))
    }.catch { e ->
        emit(Result.failure(e))
    }

    override fun getMoviesByQuery(query: String): Flow<Result<List<Movie>>> = flow {
        val encodedQuery = URLEncoder.encode(query, "utf-8")
        val response = apiService.getMovieByQuery(encodedQuery).results
        val movies = mutableListOf<Movie>()
        response?.forEach { movieResponse ->
            movies.add(movieResponse.mapToMovie())
        }

        emit(Result.success(movies))
    }.catch { e ->
        emit(Result.failure(e))

    }

    override fun getAllFavoriteMovies(): Flow<List<Movie>> = flow {
        movieDao.getAllFavoriteMovies().collect { movieEntities ->
            val movies = mutableListOf<Movie>()
            movieEntities.forEach { movieEntity ->
                movies.add(movieEntity.mapToMovie())
            }
            emit(movies)
        }
    }

    override fun isFavoriteMovies(id: Int): Flow<Boolean> = movieDao.isFavoriteMovies(id)

    override suspend fun saveMoviesAsFavorite(movie: Movie) {
        movieDao.saveMoviesAsFavorite(movie.mapToMovieEntity())
    }

    override suspend fun deleteMoviesFromFavorite(id: Int) {
        movieDao.deleteMoviesFromFavorite(id)
    }

}