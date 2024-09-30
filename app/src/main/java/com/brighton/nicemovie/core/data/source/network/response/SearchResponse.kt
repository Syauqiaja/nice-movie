package com.brighton.nicemovie.core.data.source.network.response

import com.google.gson.annotations.SerializedName

data class SearchResponse<T>(
    @field:SerializedName("totalResults")
    val totalResults: String,

    @field:SerializedName("Response")
    val response: String,

    @field:SerializedName("Search")
    val results: List<T>,
)
