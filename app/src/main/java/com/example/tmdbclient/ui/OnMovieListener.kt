package com.example.tmdbclient.ui

import com.example.tmdbclient.data.model.remote.movie_dto.Movie

interface OnMovieClickListener {

    fun onMovieClick(movie: Movie)

}