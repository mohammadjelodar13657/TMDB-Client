package com.example.tmdbclient.data.remote

import com.example.tmdbclient.data.model.remote.movie_details_dto.MovieDetailsResponse
import com.example.tmdbclient.data.model.remote.movie_dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getPopularMovieDetail(
        @Path("movie_id") id: Int
    ): MovieDetailsResponse

}