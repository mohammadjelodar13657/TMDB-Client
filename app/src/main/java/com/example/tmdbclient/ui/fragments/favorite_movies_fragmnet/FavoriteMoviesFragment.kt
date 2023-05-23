package com.example.tmdbclient.ui.fragments.favorite_movies_fragmnet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbclient.R
import com.example.tmdbclient.databinding.FragmentFavoriteMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {

    private lateinit var dataBinding: FragmentFavoriteMoviesBinding
    private val favoriteMoviesViewModel: FavoriteMoviesViewModel by viewModels()
    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_movies, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteMoviesViewModel.getAllMovies()

        favoriteMoviesAdapter = FavoriteMoviesAdapter()

        dataBinding.favoriteMoviesRv.apply {
            adapter = favoriteMoviesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        favoriteMoviesViewModel.moviesList.observe(viewLifecycleOwner) {
            favoriteMoviesAdapter.submitList(it)
        }

    }
}