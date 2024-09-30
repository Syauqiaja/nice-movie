package com.brighton.nicemovie.domain.movie.usecase

import com.brighton.nicemovie.core.data.Resource
import com.brighton.nicemovie.domain.movie.model.Movie
import com.syauqi.watcheez.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {
    suspend fun setFavorite(movie: MovieEntity)
    suspend fun deleteFavorite(movie: MovieEntity)
    fun getFavorites(): Flow<List<MovieEntity>>
}