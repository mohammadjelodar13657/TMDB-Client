package com.example.tmdbclient.ui.popular_movies_fragment

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdbclient.data.model.remote.movie_dto.Movie
import com.example.tmdbclient.data.remote.TMDBApi

const val STARTING_INDEX = 1

class PopularMoviesPagingSource(private val tmdbApi: TMDBApi) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val position = params.key ?: STARTING_INDEX

        val data = tmdbApi.getPopularMovies(STARTING_INDEX)
        return LoadResult.Page(
            data = data.movies,
            prevKey = if (params.key == STARTING_INDEX) null else position - 1,
            nextKey = if (data.movies.isEmpty()) null else position + 1
        )
    }
}