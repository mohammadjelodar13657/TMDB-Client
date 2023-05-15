package com.example.tmdbclient.ui.fragments.popular_movies_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdbclient.BR
import com.example.tmdbclient.data.model.popula_rmovie_dto.PopularMovie
import com.example.tmdbclient.databinding.PopularMoviesItemBinding

class PopularMoviesAdapter: ListAdapter<PopularMovie, PopularMoviesAdapter.PopularMoviesViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<PopularMovie>() {
            override fun areItemsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    inner class PopularMoviesViewHolder(val itemDataBinding: PopularMoviesItemBinding): RecyclerView.ViewHolder(itemDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val binding = PopularMoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        val popularMovieItem = getItem(position)

        holder.itemDataBinding.setVariable(BR.popularMovie, popularMovieItem)

        Glide.with(holder.itemDataBinding.root)
            .load("https://image.tmdb.org/t/p/w500${popularMovieItem.posterPath}")
            .into(holder.itemDataBinding.popularMovieItemIv)
    }
}