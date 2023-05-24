package com.example.tmdbclient.ui.upcoming_movie_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbclient.R
import com.example.tmdbclient.data.model.remote.movie_dto.Movie
import com.example.tmdbclient.databinding.FragmentUpcomingMovieBinding
import com.example.tmdbclient.ui.OnMovieClickListener
import com.example.tmdbclient.ui.SaveMovie
import com.example.tmdbclient.ui.favorite_movies_fragmnet.FavoriteMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingMovieFragment : Fragment(), OnMovieClickListener, SaveMovie {

    private lateinit var dataBinding: FragmentUpcomingMovieBinding
    private val upcomingMovieViewModel: UpcomingMovieViewModel by viewModels()
    private lateinit var upcomingMovieAdapter: UpcomingMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming_movie, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingMovieViewModel.getUpcomingMovies()

        upcomingMovieAdapter = UpcomingMovieAdapter(this, this)

        dataBinding.upcomingMovieRecyclerView.apply {
            adapter = upcomingMovieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        upcomingMovieViewModel.upcomingMoviesList.observe(viewLifecycleOwner) {
            upcomingMovieAdapter.submitList(it.toMutableList())
        }

        setPagination()

    }

    private fun setPagination() {
        dataBinding.upcomingMovieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == upcomingMovieAdapter.itemCount - 1) {
                    upcomingMovieViewModel.nextPage()
                }
            }
        })
    }

    override fun onMovieClick(movie: Movie) {
        val id = movie.id
        val bundle = bundleOf("id" to id)
        view?.findNavController()?.navigate(R.id.action_upcomingMovies_to_movieDetailsFragment, bundle)
    }

    override fun saveMovie(movie: Movie) {
        upcomingMovieViewModel.insertMovie(movie)
    }
}