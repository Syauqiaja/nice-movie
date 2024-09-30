package com.brighton.nicemovie.domain.movie.usecase

import androidx.paging.PagingData
import com.brighton.nicemovie.core.data.Resource
import com.brighton.nicemovie.core.data.source.network.response.movie.MovieDetailResponse
import com.brighton.nicemovie.domain.movie.model.Movie
import com.brighton.nicemovie.domain.movie.model.MovieDetail
import com.syauqi.watcheez.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovies(query:String?): Flow<PagingData<Movie>>
    fun getMovieDetail(movieId: String): Flow<Resource<MovieDetail>>
}