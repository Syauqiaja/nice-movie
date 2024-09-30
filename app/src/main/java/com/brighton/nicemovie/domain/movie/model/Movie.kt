package com.brighton.nicemovie.domain.movie.model

import android.os.Parcelable
import com.syauqi.watcheez.core.data.source.local.entity.MovieEntity
import kotlinx.parcelize.Parcelize


@Parcelize
data class Movie(
    val imdbID: String,
    val title: String,
    val year: String,
    val type: String,
    val poster: String,
):Parcelable

fun Movie.toEntity() : MovieEntity = MovieEntity(
    imdbID = imdbID,
    title = title,
    year = year,
    type = type,
    poster = poster
)