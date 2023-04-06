package com.dicoding.core.utils

import com.dicoding.core.data.remote.local.entity.MoviesEntity
import com.dicoding.core.data.remote.respon.CastResponse
import com.dicoding.core.data.remote.respon.MovieResponse
import com.dicoding.core.domain.model.Actrees
import com.dicoding.core.domain.model.Movie

fun MoviesEntity.mapToMovie(): Movie =
    Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage
    )

fun MovieResponse.mapToMovie(): Movie =
    Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage
    )

fun Movie.mapToMovieEntity(): MoviesEntity =
    MoviesEntity(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath.toString(),
        voteAverage = this.voteAverage ,
        isFavorite = false
    )

fun CastResponse.mapToCast(): Actrees =
    Actrees(
        id = this.id,
        name = this.name,
        character = this.character,
        profilePath = this.profilePath
    )