package com.example.tmdbclient.ui.fragments.movie_details_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.tmdbclient.R
import com.example.tmdbclient.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var dataBinding: FragmentMovieDetailsBinding
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("id")!!

        movieDetailsViewModel.apply {
            getMovieDetails(id)
            movieDetails.observe(viewLifecycleOwner) {
                dataBinding.apply {
                    Glide.with(movieDetailIv).load("https://image.tmdb.org/t/p/w500${it.posterPath}").into(movieDetailIv)
                    movieDetailsTitle.text = it.title
                    ratingBar.rating = it.voteAverage / 2
                    movieDetailsDescription.text = it.overview
                }
            }
        }
    }
}