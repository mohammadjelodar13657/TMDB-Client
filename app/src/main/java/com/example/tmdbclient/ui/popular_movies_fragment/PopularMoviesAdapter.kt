package com.example.tmdbclient.ui.popular_movies_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdbclient.data.model.remote.movie_dto.Movie
import com.example.tmdbclient.databinding.MovieListItemBinding
import com.example.tmdbclient.ui.OnMovieClickListener
import com.example.tmdbclient.ui.SaveMovie

class PopularMoviesAdapter(private val onMovieClickListener: OnMovieClickListener,
                           private val saveMovie: SaveMovie): ListAdapter<Movie, PopularMoviesAdapter.PopularMoviesViewHolder>(
    DIFF_UTIL
) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    inner class PopularMoviesViewHolder(val popularItemDataBinding: MovieListItemBinding): RecyclerView.ViewHolder(popularItemDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        val popularMovieItem = getItem(position)

        holder.popularItemDataBinding.apply {
            cardView.setOnClickListener {
                onMovieClickListener.onMovieClick(popularMovieItem)
            }
            saveMovieItem.setOnClickListener {
                saveMovie.saveMovie(popularMovieItem)
            }
            movieTitle.text = popularMovieItem.title
            ratingBar.rating = popularMovieItem.voteAverage / 2
            movieReleaseDate.text = popularMovieItem.releaseDate
            voteCount.text = popularMovieItem.voteCount.toString()
        }

        Glide.with(holder.popularItemDataBinding.root)
            .load("https://image.tmdb.org/t/p/w500${popularMovieItem.posterPath}")
            .into(holder.popularItemDataBinding.movieImg)
    }
}