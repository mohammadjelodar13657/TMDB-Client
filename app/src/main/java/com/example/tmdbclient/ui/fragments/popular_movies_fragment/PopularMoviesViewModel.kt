package com.example.tmdbclient.ui.fragments.popular_movies_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.tmdbclient.data.model.local.MovieEntity
import com.example.tmdbclient.data.model.remote.movie_dto.Movie
import com.example.tmdbclient.data.repository.remote.TMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val tmdbRepository: TMDBRepository
    ) : ViewModel() {

    private val _popularMoviesPaging = MutableLiveData<PagingData<Movie>>()
    val popularMoviesPaging = _popularMoviesPaging

    fun getMovies() {
        viewModelScope.launch {
            tmdbRepository.popularMoviesPaging(1).collect() {
                _popularMoviesPaging.postValue(it)
            }
        }
    }

    private val _popularMoviesList = MutableLiveData<List<Movie>>()
    val popularMoviesList: LiveData<List<Movie>> = _popularMoviesList

    val popularMovies = arrayListOf<Movie>()

    private var currentPage = 1

    fun getPopularMovies() {
        viewModelScope.launch {
            val response = tmdbRepository.getPopularMovies(currentPage).movies
            popularMovies.addAll(response)
            _popularMoviesList.value = popularMovies
        }
    }

    fun nextPage() {
        currentPage++
        getPopularMovies()
    }

    fun insertMovie(movie: Movie) {
        val movieEntity = MovieEntity(movie.id, movie.title, movie.posterPath, movie.releaseDate, movie.voteCount, movie.voteAverage)
        viewModelScope.launch {
            tmdbRepository.insertMovie(movieEntity)
        }
    }

}