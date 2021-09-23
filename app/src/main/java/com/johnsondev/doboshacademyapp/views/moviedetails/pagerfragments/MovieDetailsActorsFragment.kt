package com.johnsondev.doboshacademyapp.views.moviedetails.pagerfragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorsAdapter
import com.johnsondev.doboshacademyapp.adapters.OnActorItemClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_HORIZONTAL
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.viewmodel.MovieDetailsViewModel
import com.johnsondev.doboshacademyapp.views.moviedetails.MoviesDetailsFragmentDirections

class MovieDetailsActorsFragment : BaseFragment() {


    private lateinit var detailsViewModel: MovieDetailsViewModel
    private lateinit var rvMovieActors: RecyclerView
    private lateinit var movieActorsAdapter: ActorsAdapter

    override fun initViews(view: View) {
        detailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

        rvMovieActors = view.findViewById(R.id.rv_movie_actors)
        movieActorsAdapter = ActorsAdapter(
            requireContext(), actorClickListener,
            ITEM_TYPE_HORIZONTAL
        )
        rvMovieActors.layoutManager = LinearLayoutManager(requireContext())
        rvMovieActors.adapter = movieActorsAdapter
    }

    override fun layoutId(): Int = R.layout.fragment_movie_details_actors

    override fun loadData() {

    }

    override fun bindViews(view: View) {

    }

    override fun initListenersAndObservers(view: View) {

        detailsViewModel.getActorsForCurrentMovie().observeOnce(this, {
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