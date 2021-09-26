package com.johnsondev.doboshacademyapp.ui.actordetails.pagerfragments

import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnMovieItemClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ITEM_LARGE
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.ui.actordetails.ActorDetailsViewModel
import com.johnsondev.doboshacademyapp.ui.actordetails.ActorDetailsFragmentDirections


class ActorDetailsMoviesFragment : BaseFragment() {

    private val viewModel by viewModels<ActorDetailsViewModel>()
    private lateinit var rvMoviesList: RecyclerView
    private lateinit var moviesListAdapter: MoviesAdapter
    private lateinit var loadingIndicator: ProgressBar


    override fun initViews(view: View) {
        loadingIndicator = view.findViewById(R.id.movies_list_loading_indicator)
        rvMoviesList = view.findViewById(R.id.rv_details_movies_list)
        moviesListAdapter = MoviesAdapter(requireContext(), onMovieClickListener, MOVIE_ITEM_LARGE)
        rvMoviesList.adapter = moviesListAdapter
        rvMoviesList.layoutManager =
            LinearLayoutManager(requireContext())

    }

    override fun layoutId(): Int = R.layout.fragment_movie_details_list

    override fun loadData() {}

    override fun bindViews(view: View) {}

    override fun initListenersAndObservers(view: View) {
        viewModel.getActorMovieCredits().observeOnce(this, {
            loadingIndicator.visibility = View.GONE
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