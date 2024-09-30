package com.brighton.nicemovie.core.data.source.network.response.movie

import com.brighton.nicemovie.domain.movie.model.MovieDetail
import com.brighton.nicemovie.domain.movie.model.Rating
import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(

	@field:SerializedName("Metascore")
	val metascore: String? = null,

	@field:SerializedName("BoxOffice")
	val boxOffice: String? = null,

	@field:SerializedName("Website")
	val website: String? = null,

	@field:SerializedName("imdbRating")
	val imdbRating: String? = null,

	@field:SerializedName("imdbVotes")
	val imdbVotes: String? = null,

	@field:SerializedName("Ratings")
	val ratings: List<RatingsItem?>? = null,

	@field:SerializedName("Runtime")
	val runtime: String? = null,

	@field:SerializedName("Language")
	val language: String? = null,

	@field:SerializedName("Rated")
	val rated: String? = null,

	@field:SerializedName("Production")
	val production: String? = null,

	@field:SerializedName("Released")
	val released: String? = null,

	@field:SerializedName("imdbID")
	val imdbID: String? = null,

	@field:SerializedName("Plot")
	val plot: String? = null,

	@field:SerializedName("Director")
	val director: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Actors")
	val actors: String? = null,

	@field:SerializedName("Response")
	val response: String? = null,

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("Awards")
	val awards: String? = null,

	@field:SerializedName("DVD")
	val dVD: String? = null,

	@field:SerializedName("Year")
	val year: String? = null,

	@field:SerializedName("Poster")
	val poster: String? = null,

	@field:SerializedName("Country")
	val country: String? = null,

	@field:SerializedName("Genre")
	val genre: String? = null,

	@field:SerializedName("Writer")
	val writer: String? = null
)

data class RatingsItem(

	@field:SerializedName("Value")
	val value: String? = null,

	@field:SerializedName("Source")
	val source: String? = null
)

fun MovieDetailResponse.toMovieDetail(): MovieDetail {
	return MovieDetail(
		imdbID = this.imdbID.orEmpty(),
		plot = this.plot.orEmpty(),
		director = this.director.orEmpty(),
		title = this.title.orEmpty(),
		metascore = this.metascore.orEmpty(),
		boxOffice = this.boxOffice.orEmpty(),
		imdbRating = this.imdbRating.orEmpty(),
		imdbVotes = this.imdbVotes.orEmpty(),
		ratings = this.ratings?.mapNotNull { it?.toRating() }, // Convert ratings from RatingsItem to Rating model
		runtime = this.runtime,
		language = this.language.orEmpty(),
		rated = this.rated.orEmpty(),
		production = this.production.orEmpty(),
		awards = this.awards,
		year = this.year.orEmpty(),
		poster = this.poster.orEmpty(),
		country = this.country.orEmpty(),
		genre = this.genre.orEmpty()
	)
}

fun RatingsItem.toRating(): Rating {
	return Rating(
		value = this.value.orEmpty(),
		source = this.source.orEmpty()
	)
}