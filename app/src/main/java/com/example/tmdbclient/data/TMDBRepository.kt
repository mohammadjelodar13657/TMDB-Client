package com.example.tmdbclient.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tmdbclient.data.local.TMDBClientDao
import com.example.tmdbclient.data.model.local.MovieEntity
import com.example.tmdbclient.data.model.remote.movie_details_dto.MovieDetailsResponse
import com.example.tmdbclient.data.model.remote.movie_dto.Movie
import com.example.tmdbclient.data.model.remote.movie_dto.MovieResponse
import com.example.tmdbclient.data.remote.TMDBApi
import com.example.tmdbclient.ui.popular_movies_fragment.PopularMoviesPagingSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TMDBRepository @Inject constructor(
    private val tmdbApi: TMDBApi,
    private val tmdbClientDao: TMDBClientDao
) {

    fun popularMoviesPaging(page: Int): Flow<PagingData<Movie>> {
            return Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    PopularMoviesPagingSource(tmdbApi)
                }
            ).flow
    }


    suspend fun getUpcomingMovies(page: Int): MovieResponse {
        return withContext(IO) {
            tmdbApi.getUpcomingMovies(page)
        }
    }

    suspend fun getMovieDetails(id: Int): MovieDetailsResponse {
        return withContext(IO) {
            tmdbApi.getPopularMovieDetail(id)
        }
    }

    suspend fun insertMovie(movie: MovieEntity) {

        tmdbClientDao.insertMovie(movie)

    }

    fun getAllMovies(): Flow<List<MovieEntity>> {

        return tmdbClientDao.getAllMovies()

    }
}