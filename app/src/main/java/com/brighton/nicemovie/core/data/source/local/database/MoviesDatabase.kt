package com.syauqi.watcheez.core.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.syauqi.watcheez.core.data.source.local.dao.MoviesDao
import com.syauqi.watcheez.core.data.source.local.dao.RemoteKeysDao
import com.syauqi.watcheez.core.data.source.local.entity.FavoriteEntity
import com.syauqi.watcheez.core.data.source.local.entity.MovieEntity
import com.syauqi.watcheez.core.data.source.local.entity.RemoteKey

@Database(
    entities = [
        MovieEntity::class,
        RemoteKey::class,
        FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class  MoviesDatabase :RoomDatabase(){
    abstract fun getMovieDao(): MoviesDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}