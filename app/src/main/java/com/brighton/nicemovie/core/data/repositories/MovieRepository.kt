package com.syauqi.watcheez.core.data.repositories

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.brighton.nicemovie.core.data.MovieRemoteMediator
import com.brighton.nicemovie.core.data.Resource
import com.brighton.nicemovie.core.data.source.network.api.ApiService
import com.brighton.nicemovie.core.data.source.network.response.movie.MovieDetailResponse
import com.brighton.nicemovie.core.data.source.network.response.movie.toMovieDetail
import com.brighton.nicemovie.domain.movie.model.Movie
import com.brighton.nicemovie.domain.movie.model.MovieDetail
import com.brighton.nicemovie.domain.movie.repository.IMovieRepository
import com.syauqi.watcheez.core.data.source.local.database.MoviesDatabase
import com.syauqi.watcheez.core.data.source.local.entity.FavoriteEntity
import com.syauqi.watcheez.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRepository @Inject constructor(
    private val apiService: ApiService,
    private val movieDatabase: MoviesDatabase
): IMovieRepository {
    override fun getMovies(query: String?): Flow<PagingData<MovieEntity>> {
        val pagingSourceFactory = { movieDatabase.getMovieDao().getAllMovies() }
        Log.d("MovieRepository", "getMovies: $query")
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = MovieRemoteMediator(apiService, movieDatabase, if(query.isNullOrBlank()) "Captain" else query),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getMovieDetail(movieId: String): Flow<Resource<MovieDetail>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getMovieDetail(movieId = movieId).awaitResponse()
            if(response.isSuccessful){
                emit(Resource.Success(response.body()!!.toMovieDetail()))
            }else{
                emit(Resource.Error(response.errorBody().toString()))
            }
        }catch (e:Exception){
            emit(Resource.Error(e.message))
        }
    }

    override suspend fun setFavorite(movie: FavoriteEntity) {
        movieDatabase.withTransaction {
            movieDatabase.getMovieDao().insertFavorite(movie)
        }
    }

    override suspend fun deleteFavorite(movie: FavoriteEntity) {
        movieDatabase.withTransaction {
            movieDatabase.getMovieDao().deleteFavorite(movie)
        }
    }

    override fun getFavorites(): Flow<List<FavoriteEntity>> {
        return movieDatabase.getMovieDao().getFavorites()
    }
}