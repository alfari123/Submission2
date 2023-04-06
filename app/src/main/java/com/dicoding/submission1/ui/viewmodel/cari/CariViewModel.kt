package com.dicoding.submission1.ui.viewmodel.cari

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CariViewModel @Inject constructor(private val moviesUseCase: MoviesUseCase) :
    ViewModel(){

    fun searchMovieByQuery(query: String): LiveData<Result<List<Movie>>> =
        moviesUseCase.getMoviesByQuery(query).asLiveData()
}