package com.johnsondev.doboshacademyapp.views.moviedetails.pagerfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnMovieItemClickListener
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.calculateSpanCount
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.viewmodel.MovieDetailsViewModel
import com.johnsondev.doboshacademyapp.views.moviedetails.MoviesDetailsFragmentDirections
import com.johnsondev.doboshacademyapp.views.movielist.MoviesListFragmentDirections


class MovieDetailsRecommendFragment : BaseFragment() {

    private lateinit var detailsViewModel: MovieDetailsViewModel
    private lateinit var rvRecommendations: RecyclerView
    private lateinit var recommendationsAdapter: MoviesAdapter


    override fun initViews(view: View) {
        detailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

        rvRecommendations = view.findViewById(R.id.rv_recommendations)
        recommendationsAdapter = MoviesAdapter(requireContext(), onMovieClickListener, true)
        rvRecommendations.adapter = recommendationsAdapter
        rvRecommendations.layoutManager =
            LinearLayoutManager(requireContext())

    }

    override fun layoutId(): Int = R.layout.fragment_movie_details_recommend

    override fun loadData() {}

    override fun bindViews(view: View) {}

    override fun initListenersAndObservers(view: View) {
        detailsViewModel.getRecommendations().observeOnce(this, {
            recommendationsAdapter.setMovies(it)
        })
    }

    private val onMovieClickListener = object : OnMovieItemClickListener {
        override fun onClick(movie: Movie) {
            findNavController().navigate(
                MoviesDetailsFragmentDirections.actionMoviesDetailsFragmentToDetailsActivity(movie.id)
            )
        }

    }


}