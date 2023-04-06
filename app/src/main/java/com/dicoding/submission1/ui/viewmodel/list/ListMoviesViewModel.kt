package com.dicoding.submission1.ui.viewmodel.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ListMoviesViewModel @Inject constructor(private val moviesUseCase: MoviesUseCase) :
    ViewModel() {

    fun getAllTrendingMovies(region: String): LiveData<Result<List<Movie>>> =
        moviesUseCase.getTrendingMovies(region).asLiveData()

    fun getAllUpcomingMovies(region: String): LiveData<Result<List<Movie>>> =
        moviesUseCase.getUpcomingMovies(region).asLiveData()

    fun getAllPopularMovies(region: String): LiveData<Result<List<Movie>>> =
        moviesUseCase.getPopularMovies(region).asLiveData()
}