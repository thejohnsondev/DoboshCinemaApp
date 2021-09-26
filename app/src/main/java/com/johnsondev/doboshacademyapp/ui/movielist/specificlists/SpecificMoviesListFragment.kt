package com.johnsondev.doboshacademyapp.ui.movielist.specificlists

import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnMovieItemClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Genre
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.ui.movielist.MoviesListViewModel
import com.johnsondev.doboshacademyapp.utilities.Constants.GENRE_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.GENRE_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIES_SEARCH_RESULT_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ITEM_LARGE
import com.johnsondev.doboshacademyapp.utilities.Constants.POPULAR_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.SEARCH_RESULT_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.SPECIFIC_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.TOP_RATED_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.UPCOMING_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


class SpecificMoviesListFragment : BaseFragment() {

    private lateinit var tvSpecListType: TextView
    private lateinit var rvSpecMoviesList: RecyclerView
    private lateinit var backViewGroup: View
    private lateinit var adapter: MoviesAdapter
    private lateinit var specType: String
    private var genre: Genre? = null

    private val moviesListViewModel by viewModels<MoviesListViewModel>()


    override fun initViews(view: View) {


        rvSpecMoviesList = view.findViewById(R.id.rv_spec_movies_list)
        backViewGroup = view.findViewById(R.id.back_to_main_view_group)
        adapter = MoviesAdapter(requireContext(), clickListener, MOVIE_ITEM_LARGE)
        rvSpecMoviesList.layoutManager = LinearLayoutManager(requireContext())
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

    @FlowPreview
    @ExperimentalCoroutinesApi
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
            SEARCH_RESULT_SPEC_TYPE -> {
                val list = arguments?.getParcelableArrayList<Movie>(MOVIES_SEARCH_RESULT_SPEC_TYPE)
                adapter.setMovies(list ?: emptyList())
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