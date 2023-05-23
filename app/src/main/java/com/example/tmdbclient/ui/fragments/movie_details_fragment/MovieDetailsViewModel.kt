package com.example.tmdbclient.ui.fragments.movie_details_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbclient.data.model.remote.movie_details_dto.MovieDetailsResponse
import com.example.tmdbclient.data.repository.remote.TMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val tmdbRepository: TMDBRepository) : ViewModel() {

    private val _movieDetails = MutableLiveData<MovieDetailsResponse>()
    val movieDetails: LiveData<MovieDetailsResponse> = _movieDetails

    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            val details = tmdbRepository.getMovieDetails(id)
            _movieDetails.postValue(details)
        }
    }
}