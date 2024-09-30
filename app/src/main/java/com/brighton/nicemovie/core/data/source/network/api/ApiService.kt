package com.brighton.nicemovie.core.data.source.network.api

import com.brighton.nicemovie.core.data.source.network.response.SearchResponse
import com.brighton.nicemovie.core.data.source.network.response.movie.MovieDetailResponse
import com.brighton.nicemovie.core.data.source.network.response.movie.MovieResponse
import com.brighton.nicemovie.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    fun getMovies(
        @Query("apikey") apiKey: String = Constants.API_KEY,
        @Query("s") query: String,
        @Query("type") type : String = "movie",
        @Query("page") page : Int
    ): Call<SearchResponse<MovieResponse>>

    @GET("/")
    fun searchMovies(
        @Query("apikey") apiKey: String = Constants.API_KEY,
        @Query("type") type : String = "movie",
        @Query("s") query: String,
        @Query("page") page : Int
    ): Call<SearchResponse<MovieResponse>>

    @GET("/")
    fun getMovieDetail(
        @Query("apikey") apiKey: String = Constants.API_KEY,
        @Query("i") movieId: String,
    ): Call<MovieDetailResponse>
}