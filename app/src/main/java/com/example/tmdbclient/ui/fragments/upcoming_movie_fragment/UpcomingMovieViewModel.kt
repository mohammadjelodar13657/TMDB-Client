package com.example.tmdbclient.ui.fragments.upcoming_movie_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbclient.data.model.local.MovieEntity
import com.example.tmdbclient.data.model.remote.movie_dto.Movie
import com.example.tmdbclient.data.repository.remote.TMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingMovieViewModel @Inject constructor(
    private val tmdbRepository: TMDBRepository
//    private val tmdbDatabaseRepository: TMDBDatabaseRepository
): ViewModel() {

    private val _upcomingMoviesList = MutableLiveData<List<Movie>>()
    val upcomingMoviesList: LiveData<List<Movie>> = _upcomingMoviesList

    private val upcomingMovies = arrayListOf<Movie>()

    private var currentPage = 1

    fun getUpcomingMovies() {
        viewModelScope.launch {
            val response = tmdbRepository.getUpcomingMovies(currentPage).movies
            upcomingMovies.addAll(response)
            _upcomingMoviesList.value = upcomingMovies
        }
    }

    fun nextPage() {
        currentPage++
        getUpcomingMovies()
    }

    fun insertMovie(movie: Movie) {
        val movieEntity = MovieEntity(movie.id, movie.title, movie.posterPath, movie.releaseDate, movie.voteCount, movie.voteAverage)
        viewModelScope.launch {
            tmdbRepository.insertMovie(movieEntity)
        }
    }

}