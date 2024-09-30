package com.syauqi.watcheez.core.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brighton.nicemovie.domain.movie.model.Movie
import com.syauqi.watcheez.core.data.source.local.entity.FavoriteEntity
import com.syauqi.watcheez.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    //MOVIES
    @Query("SELECT * FROM movies")
    fun getAllMovies(): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(moviesData: List<MovieEntity>)

    @Query("DELETE FROM movies")
    fun deleteAll()

    @Query("SELECT * FROM movies WHERE title LIKE :query")
    fun searchMovies(query: String): List<MovieEntity>

    //FAVORITES
    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(moviesData: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(moviesData: FavoriteEntity)

    @Query("SELECT * FROM favorites WHERE imdbID = :movieId")
    fun isFavorite(movieId: String): Flow<List<FavoriteEntity>>
}