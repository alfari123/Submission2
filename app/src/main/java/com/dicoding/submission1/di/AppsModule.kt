package com.dicoding.submission1.di

import com.dicoding.core.domain.usecase.MoviesInteractor
import com.dicoding.core.domain.usecase.MoviesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppsModule {

    @Binds
    @Singleton
    abstract fun provideMoviesUseCase(moviesInteractor: MoviesInteractor): MoviesUseCase
}