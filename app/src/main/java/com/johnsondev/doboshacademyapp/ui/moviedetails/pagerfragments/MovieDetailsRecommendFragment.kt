package com.johnsondev.doboshacademyapp.ui.moviedetails.pagerfragments

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnMovieItemClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.databinding.FragmentMovieDetailsListBinding
import com.johnsondev.doboshacademyapp.ui.moviedetails.MovieDetailsViewModel
import com.johnsondev.doboshacademyapp.ui.moviedetails.MovieDetailsViewModelFactory
import com.johnsondev.doboshacademyapp.ui.moviedetails.MoviesDetailsFragmentDirections
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ITEM_LARGE
import com.johnsondev.doboshacademyapp.utilities.Constants.RECOMMENDATIONS_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.SIMILAR_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MovieDetailsRecommendFragment(
    private val listType: String
) : BaseFragment(R.layout.fragment_movie_details_list), KodeinAware {

    override val kodein by kodein()

    private val factory: MovieDetailsViewModelFactory by instance()
    private val detailsViewModel: MovieDetailsViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[MovieDetailsViewModel::class.java]
    }

    private val binding by viewBinding(FragmentMovieDetailsListBinding::bind)
    private lateinit var moviesListAdapter: MoviesAdapter

    override fun initFields() {
        moviesListAdapter = MoviesAdapter(requireContext(), onMovieClickListener, MOVIE_ITEM_LARGE)
        binding.rvDetailsMoviesList.adapter = moviesListAdapter
        binding.rvDetailsMoviesList.layoutManager =
            LinearLayoutManager(requireContext())
    }

    override fun loadData() {}

    override fun bindViews() {}

    override fun initListenersAndObservers() {
        when (listType) {
            RECOMMENDATIONS_LIST_TYPE -> {
                detailsViewModel.getRecommendations().observeOnce(this, {
                    binding.moviesListLoadingIndicator.visibility = View.GONE
                    moviesListAdapter.setMovies(it)
                })
            }
            SIMILAR_LIST_TYPE -> {
                detailsViewModel.getSimilarMovies().observeOnce(this, {
                    binding.moviesListLoadingIndicator.visibility = View.GONE
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