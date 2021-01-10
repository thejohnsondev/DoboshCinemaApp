package com.johnsondev.doboshacademyapp

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class FragmentMoviesList : Fragment() {

    private var spanCount: Int? = null
    private var movieList: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            VERTICAL_SPAN_COUNT else HORIZONTAL_SPAN_COUNT

        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        movieList = view.findViewById<RecyclerView>(R.id.movie_list_rv)
        movieList?.adapter = MoviesAdapter(view.context, clickListener)
        movieList?.layoutManager = GridLayoutManager(view.context, spanCount!!)

        return view
    }

    private fun doOnClick(movie: Movie) {
        movieList?.let { rv ->
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
                    "Information about ${movie.name} missed",
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

