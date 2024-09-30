package com.brighton.nicemovie.domain.movie.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.brighton.nicemovie.core.data.Resource
import com.brighton.nicemovie.core.data.source.network.response.movie.MovieDetailResponse
import com.brighton.nicemovie.domain.movie.model.Movie
import com.brighton.nicemovie.domain.movie.model.MovieDetail
import com.brighton.nicemovie.domain.movie.repository.IMovieRepository
import com.syauqi.watcheez.core.data.source.local.entity.MovieEntity
import com.syauqi.watcheez.core.data.source.local.entity.toFavoriteEntity
import com.syauqi.watcheez.core.data.source.local.entity.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val repository: IMovieRepository): MovieUseCase, FavoriteUseCase {
    override fun getMovies(query: String?): Flow<PagingData<Movie>> {
        return repository.getMovies(query).map { it.map { it.toModel() } }
    }

    override fun getMovieDetail(movieId: String): Flow<Resource<MovieDetail>> {
        return repository.getMovieDetail(movieId)
    }

    override suspend fun setFavorite(movie: MovieEntity) {
        return repository.setFavorite(movie.toFavoriteEntity())
    }

    override suspend fun deleteFavorite(movie: MovieEntity) {
        return repository.deleteFavorite(movie.toFavoriteEntity())
    }

    override fun getFavorites(): Flow<List<MovieEntity>> {
        return repository.getFavorites().map { it.map { it.toMovieEntity() } }
    }
}