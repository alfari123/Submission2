package com.dicoding.submission1.di

import com.dicoding.core.domain.usecase.MoviesUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavModuleDepedency {

    fun moviesUseCase(): MoviesUseCase
}