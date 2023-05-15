package com.example.tmdbclient.ui.fragments.popular_movies_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbclient.data.model.popula_rmovie_dto.PopularMovie
import com.example.tmdbclient.data.repository.remote.popularmovie.PopularMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(private val popularMovieRepository: PopularMovieRepository): ViewModel() {

    private val _popularMoviesList = MutableLiveData<List<PopularMovie?>?>()
    val popularMoviesList: LiveData<List<PopularMovie?>?> = _popularMoviesList

    fun getPopularMovies() {
        viewModelScope.launch(IO) {
            _popularMoviesList.value =  popularMovieRepository.getPopularMovies(1).popularMovies
        }
    }
}