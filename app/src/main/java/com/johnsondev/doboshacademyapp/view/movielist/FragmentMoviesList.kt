package com.johnsondev.doboshacademyapp.view.movielist

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.johnsondev.doboshacademyapp.model.MoviesRepository
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.model.data.Movie
import com.johnsondev.doboshacademyapp.view.moviedetails.FragmentMoviesDetails.Companion.MOVIE_KEY
import com.johnsondev.doboshacademyapp.view.moviedetails.FragmentMoviesDetails
import com.johnsondev.doboshacademyapp.viewmodel.MovieViewModel
import com.johnsondev.doboshacademyapp.viewmodel.MovieViewModelFactory
import kotlinx.coroutines.*

class FragmentMoviesList : Fragment() {

    private lateinit var rvMovie: RecyclerView
    private lateinit var typeOfMoviesList: MaterialButtonToggleGroup
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(activity?.application!!))[MovieViewModel::class.java]

        typeOfMoviesList = view.findViewById(R.id.toggle_group)

        typeOfMoviesList.addOnButtonCheckedListener { _, checkedId, _ ->
            movieViewModel.changeMoviesList(checkedId)
        }

        typeOfMoviesList.check(R.id.btn_popular)

        rvMovie = view.findViewById(R.id.movie_list_rv)
        rvMovie.layoutManager = GridLayoutManager(view.context, calculateSpanCount())
        val adapter = MoviesAdapter(view.context, clickListener)
        rvMovie.adapter = adapter

        movieViewModel.getPopularMovies().observe(this){
            adapter.setMovies(it)
        }

        movieViewModel.getAnotherMovieList().observe(this) { movie ->
            adapter.setMovies(movie)
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    private fun calculateSpanCount(): Int {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            VERTICAL_SPAN_COUNT else HORIZONTAL_SPAN_COUNT
    }

    private fun doOnClick(movie: Movie) {
        val bundleWithMovie = Bundle()
        bundleWithMovie.putParcelable(MOVIE_KEY, movie)

        val fragmentMoviesDetails = FragmentMoviesDetails()
        fragmentMoviesDetails.arguments = bundleWithMovie

        rvMovie.let { _ ->
            fragmentManager?.beginTransaction()?.apply {
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

    private val clickListener = object : OnRecyclerItemClicked {
        override fun onClick(movie: Movie) {
            doOnClick(movie)
        }
    }

    companion object {
        const val HORIZONTAL_SPAN_COUNT = 4
        const val VERTICAL_SPAN_COUNT = 2
    }
}

