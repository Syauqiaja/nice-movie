package com.brighton.nicemovie.core.di

import android.content.Context
import androidx.room.Room
import com.syauqi.watcheez.core.data.source.local.database.MoviesDatabase
import com.syauqi.watcheez.core.data.source.local.dao.MoviesDao
import com.syauqi.watcheez.core.data.source.local.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MoviesDatabase =
        Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            "db_nice_movie"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideRemoteKeysDao(database: MoviesDatabase):RemoteKeysDao = database.getRemoteKeysDao()

    @Provides
    fun provideMoviesDao(database: MoviesDatabase): MoviesDao = database.getMovieDao()
}