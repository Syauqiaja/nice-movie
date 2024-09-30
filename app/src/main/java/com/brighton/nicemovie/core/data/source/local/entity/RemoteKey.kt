package com.syauqi.watcheez.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val prevKey: Int,
    val nextKey: Int
)