package com.example.tmdbclient.ui.fragments.popular_movies_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

//        popularMoviesViewModel.loadPopularMovies()

        popularMoviesAdapter = PopularMoviesAdapter()
//        dataBinding.popularMoviesRecyclerView.apply {
//            adapter = popularMoviesAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }

        setupRecyclerView()

        popularMoviesViewModel.popularMoviesList.observe(viewLifecycleOwner) {
            popularMoviesAdapter.submitList(it)
        }

    }

    private fun setupRecyclerView() {
        dataBinding.popularMoviesRecyclerView.apply {
            adapter = popularMoviesAdapter
            layoutManager = LinearLayoutManager(requireContext())

            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if(!recyclerView.canScrollVertically(1)) {
                        popularMoviesViewModel.loadPopularMovies()
                    }
                }
            })
        }
    }

}