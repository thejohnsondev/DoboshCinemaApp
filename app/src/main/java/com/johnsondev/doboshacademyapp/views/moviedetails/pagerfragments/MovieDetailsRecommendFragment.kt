package com.johnsondev.doboshacademyapp.views.moviedetails.pagerfragments

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnMovieItemClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ITEM_LARGE
import com.johnsondev.doboshacademyapp.utilities.Constants.RECOMMENDATIONS_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.SIMILAR_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.viewmodel.MovieDetailsViewModel
import com.johnsondev.doboshacademyapp.views.moviedetails.MoviesDetailsFragmentDirections


class MovieDetailsRecommendFragment(private val listType: String) : BaseFragment() {

    private lateinit var detailsViewModel: MovieDetailsViewModel
    private lateinit var rvMoviesList: RecyclerView
    private lateinit var moviesListAdapter: MoviesAdapter


    override fun initViews(view: View) {
        detailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

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
        when (listType) {
            RECOMMENDATIONS_LIST_TYPE -> {
                detailsViewModel.getRecommendations().observeOnce(this, {
                    moviesListAdapter.setMovies(it)
                })
            }
            SIMILAR_LIST_TYPE -> {
                detailsViewModel.getSimilarMovies().observeOnce(this, {
                    moviesListAdapter.setMovies(it)
                })
            }
        }


    }

    private val onMovieClickListener = object : OnMovieItemClickListener {
        override fun onClick(movie: Movie) {
            findNavController().navigate(
                MoviesDetailsFragmentDirections.actionMoviesDetailsFragmentToDetailsActivity2(movie.id)
            )
        }

    }


}