package com.example.tmdbclient.ui.fragments.popular_movies_fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbclient.R
import com.example.tmdbclient.databinding.FragmentPopularMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private lateinit var dataBinding: FragmentPopularMoviesBinding
    private val popularMoviesViewModel: PopularMoviesViewModel by viewModels()
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_popular_movies, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularMoviesViewModel.getPopularMovies()

        popularMoviesAdapter = PopularMoviesAdapter()
        dataBinding.popularMoviesRecyclerView.apply {
            adapter = popularMoviesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        popularMoviesViewModel.popularMoviesList.observe(viewLifecycleOwner) {
//            Log.i(TAG, "onViewCreated: ${it.toString()}")
            popularMoviesAdapter.submitList(it)
        }

    }

}