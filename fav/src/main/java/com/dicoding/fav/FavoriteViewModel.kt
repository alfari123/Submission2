package com.dicoding.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.usecase.MoviesUseCase

class FavoriteViewModel(movieUseCase: MoviesUseCase) : ViewModel() {
    val favoriteMovies = movieUseCase.getAllFavoriteMovies().asLiveData()
}