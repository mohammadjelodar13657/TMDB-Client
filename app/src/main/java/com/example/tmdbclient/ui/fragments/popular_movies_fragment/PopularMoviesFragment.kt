package com.example.tmdbclient.ui.fragments.popular_movies_fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
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
import com.example.tmdbclient.databinding.FragmentPopularMoviesBinding
import com.example.tmdbclient.ui.OnMovieClickListener
import com.example.tmdbclient.ui.SaveMovie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : Fragment(), OnMovieClickListener, SaveMovie {

    private lateinit var dataBinding: FragmentPopularMoviesBinding
    private val popularMoviesViewModel: PopularMoviesViewModel by viewModels()
//    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private lateinit var popularMoviesAdapter: PopularMoviesPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_popular_movies, container, false)
//        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularMoviesViewModel.getPopularMovies()

        popularMoviesAdapter = PopularMoviesPagingAdapter(this, this)
        dataBinding.popularMoviesRecyclerView.apply {
            adapter = popularMoviesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }



//        popularMoviesAdapter = PopularMoviesAdapter(this, this)
//        dataBinding.popularMoviesRecyclerView.apply {
//            adapter = popularMoviesAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }
//
//        popularMoviesViewModel.popularMoviesList.observe(viewLifecycleOwner) {
//            popularMoviesAdapter.submitList(it.toMutableList())
//        }

        popularMoviesViewModel.popularMoviesPaging.observe(viewLifecycleOwner) {
            popularMoviesAdapter.submitData(lifecycle, it)
        }

//        setPagination()

    }

    private fun setPagination() {
        dataBinding.popularMoviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == popularMoviesAdapter.itemCount - 1) {
                    popularMoviesViewModel.nextPage()
                }
            }
        })
    }

    override fun onMovieClick(movie: Movie) {
        val id = movie.id
        val bundle = bundleOf("id" to id)
        view?.findNavController()?.navigate(R.id.action_popularMovies_to_movieDetailsFragment, bundle)
    }

    override fun saveMovie(movie: Movie) {
        popularMoviesViewModel.insertMovie(movie)
    }

}