package com.dicoding.submission1.ui.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val moviesUseCase: MoviesUseCase) :
    ViewModel(){

    fun getNowPlayingMovies(region: String): LiveData<Result<List<Movie>>> =
        moviesUseCase.getNowPlayingMovies(region).asLiveData()

    fun getTrendingMovies(region: String): LiveData<Result<List<Movie>>> =
        moviesUseCase.getTrendingMovies(region).asLiveData()

    fun getUpcomingMovies(region: String): LiveData<Result<List<Movie>>> =
        moviesUseCase.getUpcomingMovies(region).asLiveData()

    fun getPopularMovies(region: String): LiveData<Result<List<Movie>>> =
        moviesUseCase.getPopularMovies(region).asLiveData()
}