package com.johnsondev.doboshacademyapp.views.specificlist

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.google.android.material.transition.MaterialSharedAxis
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnRecyclerItemClicked
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.POPULAR_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.SPECIFIC_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.TOP_RATED_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.UPCOMING_SPEC_TYPE
import com.johnsondev.doboshacademyapp.viewmodel.MoviesListViewModel
import com.johnsondev.doboshacademyapp.views.moviedetails.FragmentMoviesDetails
import kotlinx.android.synthetic.main.fragment_specific_list.*


class SpecificListFragment : Fragment() {

    private lateinit var tvSpecListType: TextView
    private lateinit var rvSpecMoviesList: RecyclerView
    private lateinit var backViewGroup: View
    private lateinit var adapter: MoviesAdapter
    private lateinit var specType: String
    private lateinit var moviesListViewModel: MoviesListViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_specific_list, container, false)


        moviesListViewModel = ViewModelProvider(this)[MoviesListViewModel::class.java]

        rvSpecMoviesList = view.findViewById(R.id.rv_spec_movies_list)
        backViewGroup = view.findViewById(R.id.back_to_main_view_group)
        adapter = MoviesAdapter(requireContext(), clickListener)
        rvSpecMoviesList.layoutManager = GridLayoutManager(requireContext(), calculateSpanCount())


        backViewGroup.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


        when(specType){
            POPULAR_SPEC_TYPE -> {
                moviesListViewModel.getPopularMovies().observe(viewLifecycleOwner){
                    adapter.setMovies(it)
                }
            }
            TOP_RATED_SPEC_TYPE -> {
                moviesListViewModel.getTopRatedMovies().observe(viewLifecycleOwner){
                    adapter.setMovies(it)
                }
            }
            UPCOMING_SPEC_TYPE -> {
                moviesListViewModel.getUpcomingMovies().observe(viewLifecycleOwner){
                    adapter.setMovies(it)
                }
            }
        }

        rvSpecMoviesList.adapter = adapter

        tvSpecListType = view.findViewById(R.id.spec_list_type_tv)
        tvSpecListType.transitionName = specType
        tvSpecListType.text = when(specType){
            POPULAR_SPEC_TYPE -> getString(R.string.popular_movies)
            TOP_RATED_SPEC_TYPE -> getString(R.string.top_rated_movies)
            else -> getString(R.string.upcoming_movies)
        }
        startPostponedEnterTransition()

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        specType = arguments?.getString(SPECIFIC_LIST_TYPE)!!
    }

    private val clickListener = object : OnRecyclerItemClicked {
        override fun onClick(movie: Movie, view: View) {
            doOnClick(movie, view)
        }
    }

    private fun calculateSpanCount(): Int {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            Constants.VERTICAL_SPAN_COUNT else Constants.HORIZONTAL_SPAN_COUNT
    }

    private fun doOnClick(movie: Movie, view: View) {
        val bundleWithMovie = Bundle()
        bundleWithMovie.putParcelable(Constants.MOVIE_KEY, movie)

        val fragmentMoviesDetails = FragmentMoviesDetails()
        fragmentMoviesDetails.arguments = bundleWithMovie

        rvSpecMoviesList.let {
            parentFragmentManager.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                addToBackStack(null)
                replace(R.id.main_container, fragmentMoviesDetails)
                commit()
            }
        }
    }

}