package com.johnsondev.doboshacademyapp.ui.favorite.pagerfragments

import android.content.Context
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnMovieItemClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.databinding.FragmentFavoriteMoviesBinding
import com.johnsondev.doboshacademyapp.ui.favorite.FavoriteFragmentDirections
import com.johnsondev.doboshacademyapp.ui.favorite.FavoritesViewModel
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ITEM_LARGE
import com.johnsondev.doboshacademyapp.utilities.appComponent
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.utilities.states.Loading
import javax.inject.Inject


class FavoriteMoviesFragment : BaseFragment(R.layout.fragment_favorite_movies) {

    @Inject
    lateinit var factory: FavoritesViewModel.Factory
    private val viewModel: FavoritesViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[FavoritesViewModel::class.java]
    }
    private val binding by viewBinding(FragmentFavoriteMoviesBinding::bind)
    private lateinit var moviesListAdapter: MoviesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent().inject(this)
    }

    override fun initFields() {
        with(binding) {
            favoriteMoviesLoadingIndicator.visibility = View.VISIBLE
            moviesListAdapter =
                MoviesAdapter(requireContext(), onMovieClickListener, MOVIE_ITEM_LARGE)
            rvFavoriteMoviesList.adapter = moviesListAdapter
            rvFavoriteMoviesList.layoutManager =
                LinearLayoutManager(requireContext())
        }
    }

    override fun loadData() {}

    override fun bindViews() {}

    override fun initListenersAndObservers() {
        with(binding) {
            viewModel.getFavoriteMovies().observeOnce(viewLifecycleOwner, {
                favoriteMoviesLoadingIndicator.visibility = View.GONE
                rvFavoriteMoviesList.visibility = View.VISIBLE
                moviesListAdapter.setMovies(it)
            })

            viewModel.moviesLoadingState.observeOnce(viewLifecycleOwner, {
                when (it) {
                    is Loading -> {
                        favoriteMoviesLoadingIndicator.visibility = View.VISIBLE
                        rvFavoriteMoviesList.visibility = View.GONE
                    }
                }
            })
        }
    }

    private val onMovieClickListener = object : OnMovieItemClickListener {
        override fun onClick(movie: Movie) {
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailsActivity(movie.id)
            )
        }

    }


}