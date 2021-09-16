package com.johnsondev.doboshacademyapp.views.actordetails.pagerfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnMovieItemClickListener
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.viewmodel.ActorDetailsViewModel
import com.johnsondev.doboshacademyapp.viewmodel.MovieDetailsViewModel
import com.johnsondev.doboshacademyapp.views.actordetails.ActorDetailsFragmentDirections
import com.johnsondev.doboshacademyapp.views.moviedetails.MoviesDetailsFragmentDirections


class ActorDetailsMoviesFragment : BaseFragment() {

    private val viewModel by viewModels<ActorDetailsViewModel>()
    private lateinit var rvMoviesList: RecyclerView
    private lateinit var moviesListAdapter: MoviesAdapter


    override fun initViews(view: View) {

        rvMoviesList = view.findViewById(R.id.rv_details_movies_list)
        moviesListAdapter = MoviesAdapter(requireContext(), onMovieClickListener, true)
        rvMoviesList.adapter = moviesListAdapter
        rvMoviesList.layoutManager =
            LinearLayoutManager(requireContext())

    }

    override fun layoutId(): Int = R.layout.fragment_movie_details_list

    override fun loadData() {}

    override fun bindViews(view: View) {}

    override fun initListenersAndObservers(view: View) {
        viewModel.getActorMovieCredits().observeOnce(this, {
            moviesListAdapter.setMovies(it)
        })

    }

    private val onMovieClickListener = object : OnMovieItemClickListener {
        override fun onClick(movie: Movie) {
            findNavController().navigate(
                ActorDetailsFragmentDirections.actionActorDetailsFragmentToDetailsActivity(movie.id)
            )
        }

    }

}