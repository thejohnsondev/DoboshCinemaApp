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
import com.johnsondev.doboshacademyapp.data.loadMovies
import com.johnsondev.doboshacademyapp.model.Movie
import kotlinx.coroutines.*

class FragmentMoviesList : Fragment() {

    private var spanCount: Int? = null    //I save it because at initialization it is equal to null (studio prompts to do save)
    private var movieList: RecyclerView? = null
    private val scope = CoroutineScope(Dispatchers.IO + Job())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            VERTICAL_SPAN_COUNT else HORIZONTAL_SPAN_COUNT

        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        movieList = view.findViewById(R.id.movie_list_rv)
        movieList?.adapter = MoviesAdapter(view.context, clickListener)
        movieList?.layoutManager = GridLayoutManager(view.context, spanCount!!)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope.launch {
            MovieRepository.moviesList = loadMovies(context!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel()
    }

    private fun doOnClick(movie: Movie) {
        movieList?.let { _ ->
            when (movie.id) {
                1 -> fragmentManager?.beginTransaction()?.apply {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    addToBackStack(null)
                    replace(R.id.main_container, FragmentMoviesDetails())
                    commit()
                }
                else -> Toast.makeText(
                    view?.context,
                    "${getString(R.string.inform_about)} ${movie.title} ${getString(R.string.missed)}",
                    Toast.LENGTH_SHORT
                ).show()
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

