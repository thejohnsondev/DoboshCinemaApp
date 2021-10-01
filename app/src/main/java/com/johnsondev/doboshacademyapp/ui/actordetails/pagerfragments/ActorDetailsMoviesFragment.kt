package com.johnsondev.doboshacademyapp.ui.actordetails.pagerfragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnMovieItemClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.databinding.FragmentActorDetailsMoviesBinding
import com.johnsondev.doboshacademyapp.ui.actordetails.ActorDetailsFragmentDirections
import com.johnsondev.doboshacademyapp.ui.actordetails.ActorDetailsViewModel
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ITEM_LARGE
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce

class ActorDetailsMoviesFragment : BaseFragment(R.layout.fragment_actor_details_movies) {

    private val viewModel by viewModels<ActorDetailsViewModel>()
    private val binding by viewBinding(FragmentActorDetailsMoviesBinding::bind)
    private lateinit var moviesListAdapter: MoviesAdapter

    override fun initFields() {
        moviesListAdapter = MoviesAdapter(requireContext(), onMovieClickListener, MOVIE_ITEM_LARGE)
        binding.rvActorMoviesList.adapter = moviesListAdapter
        binding.rvActorMoviesList.layoutManager =
            LinearLayoutManager(requireContext())
    }

    override fun loadData() {}

    override fun bindViews() {}

    override fun initListenersAndObservers() {
        viewModel.getActorMovieCredits().observeOnce(this, {
            binding.actorMoviesLoadingIndicator.visibility = View.GONE
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