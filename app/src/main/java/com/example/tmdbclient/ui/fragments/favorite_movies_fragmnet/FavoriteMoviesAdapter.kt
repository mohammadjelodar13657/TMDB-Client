package com.example.tmdbclient.ui.fragments.favorite_movies_fragmnet


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdbclient.data.model.local.MovieEntity
import com.example.tmdbclient.databinding.MovieListItemBinding

class FavoriteMoviesAdapter: ListAdapter<MovieEntity, FavoriteMoviesAdapter.FavoriteMoviesViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.movieId == newItem.movieId
            }
        }
    }

    inner class FavoriteMoviesViewHolder(val favoriteMoviesBinding: MovieListItemBinding): RecyclerView.ViewHolder(favoriteMoviesBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
        val movieItem = getItem(position)

        holder.favoriteMoviesBinding.apply {
            Glide.with(movieImg).load("https://image.tmdb.org/t/p/w500${movieItem.posterPath}").into(movieImg)
            movieTitle.text = movieItem.movieTitle
            movieReleaseDate.text = movieItem.releaseDate
            voteCount.text = movieItem.voteCount.toString()
            ratingBar.rating = movieItem.voteAverage / 2
            saveMovieItem.visibility = View.GONE
        }
    }
}