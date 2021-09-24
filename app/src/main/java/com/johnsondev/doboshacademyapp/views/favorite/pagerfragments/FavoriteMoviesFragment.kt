package com.johnsondev.doboshacademyapp.views.favorite.pagerfragments

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
import com.johnsondev.doboshacademyapp.utilities.states.Loading
import com.johnsondev.doboshacademyapp.viewmodel.FavoritesViewModel
import com.johnsondev.doboshacademyapp.views.favorite.FavoriteFragmentDirections


class FavoriteMoviesFragment : BaseFragment() {

    private val favoritesViewModel by viewModels<FavoritesViewModel>()
    private lateinit var rvMoviesList: RecyclerView
    private lateinit var moviesListAdapter: MoviesAdapter
    private lateinit var loadingIndicator: ProgressBar


    override fun initViews(view: View) {

        rvMoviesList = view.findViewById(R.id.rv_details_movies_list)
        loadingIndicator = view.findViewById(R.id.movies_loading_indicator)
        loadingIndicator.visibility = View.VISIBLE
        moviesListAdapter = MoviesAdapter(requireContext(), onMovieClickListener, MOVIE_ITEM_LARGE)
        rvMoviesList.adapter = moviesListAdapter
        rvMoviesList.layoutManager =
            LinearLayoutManager(requireContext())

    }

    override fun layoutId(): Int = R.layout.fragment_movie_details_list

    override fun loadData() {}

    override fun bindViews(view: View) {}

    override fun initListenersAndObservers(view: View) {
        favoritesViewModel.getFavoriteMovies().observeOnce(viewLifecycleOwner, {
            loadingIndicator.visibility = View.GONE
            rvMoviesList.visibility = View.VISIBLE
            moviesListAdapter.setMovies(it)
        })

        favoritesViewModel.moviesLoadingState.observeOnce(viewLifecycleOwner, {
            when (it) {
                is Loading -> {
                    loadingIndicator.visibility = View.VISIBLE
                    rvMoviesList.visibility = View.GONE
                }
            }
        })

    }

    private val onMovieClickListener = object : OnMovieItemClickListener {
        override fun onClick(movie: Movie) {
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailsActivity(movie.id)
            )
        }

    }


}