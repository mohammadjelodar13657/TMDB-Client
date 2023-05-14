package com.example.tmdbclient.data.remote

import com.example.tmdbclient.data.model.popula_rmovie_dto.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): PopularMoviesResponse

}