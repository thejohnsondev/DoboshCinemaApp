package com.johnsondev.doboshacademyapp

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.FragmentMoviesDetails.Companion.MOVIE_KEY
import com.johnsondev.doboshacademyapp.data.loadMovies
import com.johnsondev.doboshacademyapp.model.Movie
import kotlinx.coroutines.*

class FragmentMoviesList : Fragment() {

    private var movieList: RecyclerView? = null
    private val scope = CoroutineScope(Dispatchers.IO + Job())

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val spanCount: Int = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            VERTICAL_SPAN_COUNT else HORIZONTAL_SPAN_COUNT

        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        movieList = view.findViewById(R.id.movie_list_rv)

        movieList?.apply {
            adapter = MoviesAdapter(view.context, clickListener, MovieRepository.moviesList)
            layoutManager = GridLayoutManager(view.context, spanCount)
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope.launch {
            MovieRepository.loadMoviesToRepository(context!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel()
    }

    private fun doOnClick(movie: Movie) {
        val bundleWithMovieId = Bundle()
        bundleWithMovieId.putInt(MOVIE_KEY, movie.id)

        val fragmentMoviesDetails = FragmentMoviesDetails()
        fragmentMoviesDetails.arguments = bundleWithMovieId

        movieList?.let { _ ->
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

