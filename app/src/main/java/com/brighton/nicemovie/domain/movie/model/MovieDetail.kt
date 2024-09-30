package com.brighton.nicemovie.domain.movie.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    val imdbID: String,
    val plot: String,
    val director: String,
    val title: String,
    val metascore: String,
    val boxOffice: String,
    val imdbRating: String,
    val imdbVotes: String,
    val ratings: List<Rating>? = null,
    val runtime: String? = null,
    val language: String,
    val rated: String,
    val production: String,
    val awards: String? = null,
    val year: String,
    val poster: String,
    val country: String,
    val genre: String,
)
fun MovieDetail.parseMovieDetailToMovie(type: String = "movie"): Movie {
    return Movie(
        imdbID = imdbID,
        title = title,
        year = year,
        type = type, // Use a default type or modify as needed
        poster = poster
    )
}

