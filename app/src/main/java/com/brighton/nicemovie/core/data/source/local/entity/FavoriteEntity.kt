package com.syauqi.watcheez.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "imdbID")
    val imdbID: String,

    val title: String,
    val year: String,
    val type: String,
    val poster: String,
){
    fun toMovieEntity(): MovieEntity = MovieEntity(
        imdbID = imdbID,
        title = title,
        year = year,
        type = type,
        poster = poster
    )
}