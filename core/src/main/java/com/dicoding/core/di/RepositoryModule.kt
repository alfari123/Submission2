package com.dicoding.core.di

import com.dicoding.core.data.MoviesRepository
import com.dicoding.core.domain.repository.InterfaceMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(includes = [ApiModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideMovieRepository(moviesRepository: MoviesRepository): InterfaceMovieRepository
}