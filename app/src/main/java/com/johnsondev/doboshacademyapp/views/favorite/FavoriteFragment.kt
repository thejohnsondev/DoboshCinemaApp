package com.johnsondev.doboshacademyapp.views.favorite

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorsAdapter
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnActorItemClickListener
import com.johnsondev.doboshacademyapp.adapters.OnMovieItemClickListener
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.FAVORITES_MOVIES_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_MINI
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ITEM_MINI
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.isInternetConnectionAvailable
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.utilities.states.ValidResult
import com.johnsondev.doboshacademyapp.viewmodel.FavoritesViewModel
import java.util.ArrayList


class FavoriteFragment : BaseFragment() {

    private val favoritesViewModel by viewModels<FavoritesViewModel>()
    private var rvFavoriteMovies: RecyclerView? = null
    private var moviesAdapter: MoviesAdapter? = null
    private var rvFavoriteActors: RecyclerView? = null
    private var actorsAdapter: ActorsAdapter? = null


    override fun initViews(view: View) {
        rvFavoriteMovies = view.findViewById(R.id.favorite_movies_rv)
        moviesAdapter = MoviesAdapter(requireContext(), onMovieClickListener, MOVIE_ITEM_MINI)
        rvFavoriteMovies?.adapter = moviesAdapter
        rvFavoriteActors = view.findViewById(R.id.favorite_actors_rv)
        actorsAdapter = ActorsAdapter(requireContext(),onActorClickListener, ITEM_TYPE_MINI)
        rvFavoriteActors?.adapter =actorsAdapter
    }

    override fun layoutId(): Int = R.layout.fragment_favorite

    override fun loadData() {
        if (isInternetConnectionAvailable(activity?.application!!)) {
            favoritesViewModel.loadFavoriteMoviesFromDb()
            favoritesViewModel.loadFavoriteActorsFromDb()
        }
    }

    override fun bindViews(view: View) {

    }

    override fun initListenersAndObservers(view: View) {

        favoritesViewModel.getFavoriteMovies().observeOnce(this, {
            moviesAdapter?.setMovies(it)
        })

        favoritesViewModel.getFavoriteActors().observeOnce(this,{
            actorsAdapter?.setActors(it)
        })
    }

    private val onMovieClickListener = object : OnMovieItemClickListener {
        override fun onClick(movie: Movie) {
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailsActivity(movie.id)
            )
        }
    }

    private val onActorClickListener = object : OnActorItemClickListener{
        override fun onClick(actor: Actor) {
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToActorDetailsActivity(actor.id)
            )
        }

    }


}