package com.example.tmdbclient.ui.fragments.upcoming_movie_fragment

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

class UpcomingMovieAdapter(
    private val onMovieClickListener: OnMovieClickListener,
    private val saveMovie: SaveMovie
) :
    ListAdapter<Movie, UpcomingMovieAdapter.UpcomingMovieViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    inner class UpcomingMovieViewHolder(val upcomingItemDataBinding: MovieListItemBinding) :
        RecyclerView.ViewHolder(upcomingItemDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMovieViewHolder {
        val binding =
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingMovieViewHolder, position: Int) {
        val upcomingMovieItem = getItem(position)

//        holder.upcomingItemDataBinding.setVariable(BR.upcomingMovie, upcomingMovieItem)
        holder.upcomingItemDataBinding.apply {
            cardView.setOnClickListener {
                onMovieClickListener.onMovieClick(upcomingMovieItem)
            }
            saveMovieItem.setOnClickListener {
                saveMovie.saveMovie(upcomingMovieItem)
            }
            movieTitle.text = upcomingMovieItem.title
            ratingBar.rating = upcomingMovieItem.voteAverage / 2
            movieReleaseDate.text = upcomingMovieItem.releaseDate
            voteCount.text = upcomingMovieItem.voteCount.toString()
        }

        Glide.with(holder.upcomingItemDataBinding.root)
            .load("https://image.tmdb.org/t/p/w500${upcomingMovieItem.posterPath}")
            .into(holder.upcomingItemDataBinding.movieImg)
    }
}