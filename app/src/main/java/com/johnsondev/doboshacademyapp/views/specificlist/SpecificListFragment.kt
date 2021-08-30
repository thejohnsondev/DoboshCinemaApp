package com.johnsondev.doboshacademyapp.views.specificlist

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnRecyclerItemClicked
import com.johnsondev.doboshacademyapp.data.models.Genre
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.GENRE_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.GENRE_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.POPULAR_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.SPECIFIC_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.TOP_RATED_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.UPCOMING_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.viewmodel.MoviesListViewModel


class SpecificListFragment : BaseFragment() {

    private lateinit var tvSpecListType: TextView
    private lateinit var rvSpecMoviesList: RecyclerView
    private lateinit var backViewGroup: View
    private lateinit var adapter: MoviesAdapter
    private lateinit var specType: String
    private var genre: Genre? = null
    private lateinit var moviesListViewModel: MoviesListViewModel

    override fun initViews(view: View) {
        moviesListViewModel = ViewModelProvider(this)[MoviesListViewModel::class.java]

        rvSpecMoviesList = view.findViewById(R.id.rv_spec_movies_list)
        backViewGroup = view.findViewById(R.id.back_to_main_view_group)
        adapter = MoviesAdapter(requireContext(), clickListener, false)
        rvSpecMoviesList.layoutManager = GridLayoutManager(requireContext(), calculateSpanCount())

        rvSpecMoviesList.adapter = adapter

        tvSpecListType = view.findViewById(R.id.spec_list_type_tv)

    }

    override fun layoutId(): Int = R.layout.fragment_specific_list

    override fun loadData() {
        specType = arguments?.getString(SPECIFIC_LIST_TYPE)!!
        Log.d("TAG", "spec type == $specType")
        when (specType) {
            GENRE_SPEC_TYPE -> {

                genre = arguments?.getParcelable(GENRE_KEY)
                Log.d("TAG", genre?.name!!)
                moviesListViewModel.loadMoviesByGenreId(genre?.id ?: 0)
            }
        }
    }

    override fun bindViews(view: View) {
        tvSpecListType.transitionName = specType
        tvSpecListType.text = when (specType) {
            POPULAR_SPEC_TYPE -> getString(R.string.popular_movies)
            TOP_RATED_SPEC_TYPE -> getString(R.string.top_rated_movies)
            GENRE_SPEC_TYPE -> genre?.name
            else -> getString(R.string.upcoming_movies)
        }
    }

    override fun initListenersAndObservers(view: View) {
        backViewGroup.setOnClickListener {
            findNavController().popBackStack()
        }

        when (specType) {
            POPULAR_SPEC_TYPE -> {
                moviesListViewModel.getPopularMovies().observe(viewLifecycleOwner) {
                    adapter.setMovies(it)
                    Log.d("TAG", "spec fragment popular")
                }
            }
            TOP_RATED_SPEC_TYPE -> {
                moviesListViewModel.getTopRatedMovies().observe(viewLifecycleOwner) {
                    adapter.setMovies(it)
                    Log.d("TAG", "spec fragment top")
                }
            }
            UPCOMING_SPEC_TYPE -> {
                moviesListViewModel.getUpcomingMovies().observe(viewLifecycleOwner) {
                    adapter.setMovies(it)
                    Log.d("TAG", "spec fragment upcoming")
                }
            }
            GENRE_SPEC_TYPE -> {
                moviesListViewModel.getMoviesByGenre().observe(viewLifecycleOwner) {
                    adapter.setMovies(it)
                    Log.d("TAG", "spec fragment genre")
                }
            }
        }
    }


    private val clickListener = object : OnRecyclerItemClicked {
        override fun onClick(movie: Movie) {
            doOnClick(movie)
        }
    }

    private fun calculateSpanCount(): Int {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            Constants.VERTICAL_SPAN_COUNT else Constants.HORIZONTAL_SPAN_COUNT
    }

    private fun doOnClick(movie: Movie) {
        val bundleWithMovie = Bundle()
        bundleWithMovie.putInt(Constants.MOVIE_KEY, movie.id)

        findNavController().navigate(
            R.id.action_specificListFragment_to_moviesDetailsFragment,
            bundleWithMovie
        )


    }

}