package com.example.tmdbclient.data.repository.remote.popularmovie

import com.example.tmdbclient.data.remote.TMDBApi
import dagger.hilt.android.AndroidEntryPoint

class PopularMovieRepository(private val tmdbApi: TMDBApi) {

    suspend fun getPopularMovies(page: Int) = tmdbApi.getPopularMovies(page)

}