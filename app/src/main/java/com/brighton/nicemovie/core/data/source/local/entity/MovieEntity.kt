package com.syauqi.watcheez.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brighton.nicemovie.domain.movie.model.Movie
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "imdbID")
    val imdbID: String,

    val title: String,
    val year: String,
    val type: String,
    val poster: String,
)
fun MovieEntity.toFavoriteEntity(): FavoriteEntity = FavoriteEntity(
    imdbID = imdbID,
    title = title,
    year = year,
    type = type,
    poster = poster
)
fun MovieEntity.toModel(): Movie = Movie(
    imdbID = imdbID,
    title = title,
    year = year,
    type = type,
    poster = poster
)