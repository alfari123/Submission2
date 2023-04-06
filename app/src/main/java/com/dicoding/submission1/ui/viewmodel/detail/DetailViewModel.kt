package com.dicoding.submission1.ui.viewmodel.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.core.domain.model.Actrees
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val movieUseCase: MoviesUseCase) :
    ViewModel(){

    fun getRelatedMovies(id: Int): LiveData<Result<List<Movie>>> =
        movieUseCase.getRelatedMovies(id).asLiveData()

    fun getMovieCasts(id: Int): LiveData<Result<List<Actrees>>> =
        movieUseCase.getMoviesActrees(id).asLiveData()

    fun saveMovieAsFavorite(movie: Movie) {
        viewModelScope.launch {
            movieUseCase.saveMoviesAsFavorite(movie)
        }
    }

    fun deleteMovieFromFavorite(id: Int) {
        viewModelScope.launch {
            movieUseCase.deleteMoviesFromFavorite(id)
        }
    }

    fun isFavoriteMovie(id: Int): LiveData<Boolean> =
        movieUseCase.isFavoriteMovies(id).asLiveData()
}