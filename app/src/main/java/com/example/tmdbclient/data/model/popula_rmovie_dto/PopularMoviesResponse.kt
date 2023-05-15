package com.example.tmdbclient.data.model.popula_rmovie_dto


import com.google.gson.annotations.SerializedName

data class PopularMoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val popularMovies: List<PopularMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)