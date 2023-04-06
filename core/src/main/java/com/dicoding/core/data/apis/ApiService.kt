package com.dicoding.core.data.apis

import com.dicoding.core.BuildConfig.API_KEY
import com.dicoding.core.data.remote.respon.ListCastResponse
import com.dicoding.core.data.remote.respon.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("region") region: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("region") region: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("region") region: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("region") region: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRelatedMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCasts(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ListCastResponse

    @GET("search/movie")
    suspend fun getMovieByQuery(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse
}