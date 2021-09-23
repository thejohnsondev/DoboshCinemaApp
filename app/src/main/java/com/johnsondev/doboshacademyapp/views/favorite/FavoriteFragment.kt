package com.johnsondev.doboshacademyapp.views.favorite

import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorsAdapter
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnActorItemClickListener
import com.johnsondev.doboshacademyapp.adapters.OnMovieItemClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_MINI
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ITEM_MINI
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.isInternetConnectionAvailable
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.utilities.states.Loading
import com.johnsondev.doboshacademyapp.viewmodel.FavoritesViewModel


class FavoriteFragment : BaseFragment() {

    private val favoritesViewModel by viewModels<FavoritesViewModel>()
    private var rvFavoriteMovies: RecyclerView? = null
    private var moviesAdapter: MoviesAdapter? = null
    private var rvFavoriteActors: RecyclerView? = null
    private var actorsAdapter: ActorsAdapter? = null
    private var favMoviesLoadingIndicator: ProgressBar? = null
    private var favActorsLoadingIndicator: ProgressBar? = null
    private var favMoviesSpecBtn: View? = null
    private var favActorsSpecBtn: View? = null


    override fun initViews(view: View) {
        rvFavoriteMovies = view.findViewById(R.id.favorite_movies_rv)
        moviesAdapter = MoviesAdapter(requireContext(), onMovieClickListener, MOVIE_ITEM_MINI)
        rvFavoriteMovies?.adapter = moviesAdapter
        rvFavoriteActors = view.findViewById(R.id.favorite_actors_rv)
        actorsAdapter = ActorsAdapter(requireContext(), onActorClickListener, ITEM_TYPE_MINI)
        rvFavoriteActors?.adapter = actorsAdapter
        favMoviesLoadingIndicator = view.findViewById(R.id.favorite_movies_loading_indicator)
        favActorsLoadingIndicator = view.findViewById(R.id.favorite_actors_loading_indicator)
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
            favMoviesLoadingIndicator?.visibility = View.GONE
            rvFavoriteMovies?.visibility = View.VISIBLE
            moviesAdapter?.setMovies(it)

        })

        favoritesViewModel.getFavoriteActors().observeOnce(this, {
            favActorsLoadingIndicator?.visibility = View.GONE
            rvFavoriteActors?.visibility = View.VISIBLE
            actorsAdapter?.setActors(it)

        })

        favoritesViewModel.moviesLoadingState.observeOnce(this, {
            when (it) {
                is Loading -> {
                    favMoviesLoadingIndicator?.visibility = View.VISIBLE
                    rvFavoriteMovies?.visibility = View.GONE
                }
            }
        })

        favoritesViewModel.actorsLoadingState.observeOnce(this, {
            when (it) {
                is Loading -> {
                    favActorsLoadingIndicator?.visibility = View.VISIBLE
                    rvFavoriteActors?.visibility = View.GONE

                }
            }
        })

        favoritesViewModel.error.observe(viewLifecycleOwner){
            if(it != null){
                onError(it)
                favActorsLoadingIndicator?.isVisible = false
                favMoviesLoadingIndicator?.isVisible = false
                favMoviesSpecBtn?.isVisible = false
                favActorsSpecBtn?.isVisible = false
            }
        }

        favMoviesSpecBtn?.setOnClickListener {

        }

        favActorsSpecBtn?.setOnClickListener {

        }
    }


    private val onMovieClickListener = object : OnMovieItemClickListener {
        override fun onClick(movie: Movie) {
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailsActivity(movie.id)
            )
        }
    }

    private val onActorClickListener = object : OnActorItemClickListener {
        override fun onClick(actor: Actor) {
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToActorDetailsActivity(actor.id)
            )
        }

    }


}