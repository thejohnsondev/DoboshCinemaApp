package com.johnsondev.doboshacademyapp.ui.moviedetails.pagerfragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorsAdapter
import com.johnsondev.doboshacademyapp.adapters.OnActorItemClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.databinding.FragmentMovieDetailsActorsBinding
import com.johnsondev.doboshacademyapp.ui.moviedetails.MovieDetailsViewModel
import com.johnsondev.doboshacademyapp.ui.moviedetails.MovieDetailsViewModelFactory
import com.johnsondev.doboshacademyapp.ui.moviedetails.MoviesDetailsFragmentDirections
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_HORIZONTAL
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MovieDetailsActorsFragment : BaseFragment(R.layout.fragment_movie_details_actors),
    KodeinAware {

    override val kodein by kodein()

    private val factory: MovieDetailsViewModelFactory by instance()
    private val detailsViewModel: MovieDetailsViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[MovieDetailsViewModel::class.java]
    }

    private val binding by viewBinding(FragmentMovieDetailsActorsBinding::bind)
    private lateinit var movieActorsAdapter: ActorsAdapter

    override fun initFields() {
        movieActorsAdapter = ActorsAdapter(
            requireContext(), actorClickListener,
            ITEM_TYPE_HORIZONTAL
        )
        binding.rvMovieActors.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMovieActors.adapter = movieActorsAdapter
    }

    override fun loadData() {}

    override fun bindViews() {}

    override fun initListenersAndObservers() {

        detailsViewModel.getActorsForCurrentMovie().observeOnce(this, {
            binding.movieActorsLoadingIndicator.visibility = View.GONE
            movieActorsAdapter.setActors(it)
        })
    }

    private val actorClickListener = object : OnActorItemClickListener {
        override fun onClick(actor: Actor) {
            val bundleWithActor = Bundle()
            bundleWithActor.putParcelable(Constants.ACTOR_KEY, actor)
            findNavController().navigate(
                MoviesDetailsFragmentDirections.actionMoviesDetailsFragmentToActorDetailsActivity(
                    actor.id
                )
            )
        }
    }
}