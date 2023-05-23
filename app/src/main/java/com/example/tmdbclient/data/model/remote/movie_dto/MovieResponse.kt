package com.example.tmdbclient.data.model.remote.movie_dto

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val movies: List<Movie>
)