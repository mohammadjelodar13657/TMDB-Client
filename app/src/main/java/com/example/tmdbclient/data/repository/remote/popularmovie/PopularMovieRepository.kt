package com.example.tmdbclient.data.repository.remote.popularmovie

import com.example.tmdbclient.data.model.popula_rmovie_dto.PopularMovie
import com.example.tmdbclient.data.remote.TMDBApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PopularMovieRepository(private val tmdbApi: TMDBApi) {

//    suspend fun getPopularMovies(page: Int) = tmdbApi.getPopularMovies(page)

    private var currentPage = 1
    fun getPopularMovies(): Flow<List<PopularMovie>> {
        return flow {
//            var page = 1
//            var totalPages = 1
            while (true) {
                val response = tmdbApi.getPopularMovies(currentPage)
//                totalPages = response.totalPages
                val popularMovies = response.popularMovies
                emit(popularMovies)
                currentPage++
            }
        }.flowOn(IO)
    }
}