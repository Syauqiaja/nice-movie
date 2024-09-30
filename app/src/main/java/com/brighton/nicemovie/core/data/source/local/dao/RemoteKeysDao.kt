package com.syauqi.watcheez.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syauqi.watcheez.core.data.source.local.entity.RemoteKey

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: Int): RemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteRemoteKeys()
}