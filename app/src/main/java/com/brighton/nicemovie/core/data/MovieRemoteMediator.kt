package com.brighton.nicemovie.core.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.brighton.nicemovie.core.data.source.network.api.ApiService
import com.syauqi.watcheez.core.data.source.local.database.MoviesDatabase
import com.syauqi.watcheez.core.data.source.local.entity.MovieEntity
import retrofit2.await
import retrofit2.awaitResponse

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val apiService: ApiService,
    private val movieDatabase: MoviesDatabase, // Replace this with your database
    private val query: String
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastPage = getLastPage(state)
                lastPage + 1
            }
        }

        return try {
            Log.d("MovieRemoteMediator", "loading")
            val response = apiService.getMovies(
                query = query,
                page = page
            ).awaitResponse()
            Log.d("MovieRemoteMediator", "load: $response")

            if(response.isSuccessful){
                if(response.body() != null){
                    val endOfPaginationReached = response.body()!!.results.isEmpty()
                    Log.d("MovieRemoteMediator", "success: $response")
                    movieDatabase.withTransaction {
                        Log.d("MovieRemoteMediator", "start transaction: $response")
                        if (loadType == LoadType.REFRESH) {
                            movieDatabase.getMovieDao().deleteAll() // Clear cache for refresh
                        }
                        Log.d("MovieRemoteMediator", "Creating entity: $response")
                        val movies = response.body()!!.results.map { movieResponse ->
                            MovieEntity(
                                title = movieResponse.title,
                                year = movieResponse.year,
                                type = movieResponse.type,
                                imdbID = movieResponse.imdbID,
                                poster = movieResponse.poster
                            )
                        }
                        Log.d("MovieRemoteMediator", "Saving to database: $response")
                        movieDatabase.getMovieDao().insertAll(movies)
                    }
                    Log.d("MovieRemoteMediator", "end load: $response")
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }else{
                    val endOfPaginationReached = false
                    Log.d("MovieRemoteMediator", "success: $response")
                    movieDatabase.withTransaction {
                        Log.d("MovieRemoteMediator", "start transaction: $response")
                        if (loadType == LoadType.REFRESH) {
                            movieDatabase.getMovieDao().deleteAll() // Clear cache for refresh
                        }
                        Log.d("MovieRemoteMediator", "Saving to database: $response")
                    }
                    Log.d("MovieRemoteMediator", "end load: $response")
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
            }else{
                Log.d("MovieRemoteMediator", "error: $response")
                MediatorResult.Error(Exception(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Log.e("MovieRemoteMediator", "error: $e")
            MediatorResult.Error(e)
        }
    }

    private fun getLastPage(state: PagingState<Int, MovieEntity>): Int {
        return state.pages.lastOrNull()?.nextKey ?: 1
    }
}
