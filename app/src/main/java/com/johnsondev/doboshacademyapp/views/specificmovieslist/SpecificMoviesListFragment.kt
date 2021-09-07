package com.johnsondev.doboshacademyapp.views.specificmovieslist

import android.content.res.Configuration
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnMovieItemClickListener
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
import com.johnsondev.doboshacademyapp.utilities.calculateSpanCount
import com.johnsondev.doboshacademyapp.viewmodel.MoviesListViewModel


class SpecificMoviesListFragment : BaseFragment() {

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
        rvSpecMoviesList.layoutManager =
            GridLayoutManager(requireContext(), calculateSpanCount(requireContext()))
        rvSpecMoviesList.adapter = adapter
        tvSpecListType = view.findViewById(R.id.spec_list_type_tv)

    }

    override fun layoutId(): Int = R.layout.fragment_specific_movies_list

    override fun loadData() {
        specType = arguments?.getString(SPECIFIC_LIST_TYPE)!!
        when (specType) {
            GENRE_SPEC_TYPE -> {
                genre = arguments?.getParcelable(GENRE_KEY)
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
                }
            }
            TOP_RATED_SPEC_TYPE -> {
                moviesListViewModel.getTopRatedMovies().observe(viewLifecycleOwner) {
                    adapter.setMovies(it)
                }
            }
            UPCOMING_SPEC_TYPE -> {
                moviesListViewModel.getUpcomingMovies().observe(viewLifecycleOwner) {
                    adapter.setMovies(it)
                }
            }
            GENRE_SPEC_TYPE -> {
                moviesListViewModel.getMoviesByGenre().observe(viewLifecycleOwner) {
                    adapter.setMovies(it)
                }
            }
        }
    }


    private val clickListener = object : OnMovieItemClickListener {
        override fun onClick(movie: Movie) {
            doOnClick(movie)
        }
    }


    private fun doOnClick(movie: Movie) {
        findNavController().navigate(
            SpecificMoviesListFragmentDirections.actionSpecificListFragmentToDetailsActivity(movie.id)
        )
    }

}