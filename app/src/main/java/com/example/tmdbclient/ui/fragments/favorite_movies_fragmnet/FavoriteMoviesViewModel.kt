package com.example.tmdbclient.ui.fragments.favorite_movies_fragmnet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbclient.data.model.local.MovieEntity
import com.example.tmdbclient.data.repository.remote.TMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val tmdbRepository: TMDBRepository
): ViewModel() {

    private val _moviesList = MutableLiveData<List<MovieEntity>>()
    val moviesList: LiveData<List<MovieEntity>> = _moviesList

    fun getAllMovies() {
        viewModelScope.launch {
            tmdbRepository.getAllMovies().collect() {
                _moviesList.postValue(it)
            }
        }
    }

}