package com.johnsondev.doboshacademyapp.views.movielist

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.button.MaterialButtonToggleGroup
import com.johnsondev.doboshacademyapp.App
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnRecyclerItemClicked
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.services.MovieDbUpdateWorker
import com.johnsondev.doboshacademyapp.utilities.InternetConnectionManager
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.HORIZONTAL_SPAN_COUNT
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.VERTICAL_SPAN_COUNT
import com.johnsondev.doboshacademyapp.views.moviedetails.FragmentMoviesDetails
import com.johnsondev.doboshacademyapp.viewmodel.MovieViewModel
import com.johnsondev.doboshacademyapp.viewmodel.MovieViewModelFactory
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class FragmentMoviesList : Fragment() {

    private lateinit var rvMovie: RecyclerView
    private lateinit var adapter: MoviesAdapter
    private lateinit var swipeToRefresh: SwipeRefreshLayout
    private lateinit var typeOfMoviesList: MaterialButtonToggleGroup
    private lateinit var movieViewModel: MovieViewModel
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private lateinit var checkInternetConnection: InternetConnectionManager
    private var isConnectionErrorFromBundle: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        movieViewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(activity?.application!!)
        )[MovieViewModel::class.java]

        initViews(view)
        initWorkManager()

        if (isConnectionErrorFromBundle == true && !movieViewModel.isInternetConnectionAvailable()) {
            Toast.makeText(
                context,
                getString(R.string.internet_connection_error),
                Toast.LENGTH_SHORT
            ).show()
        }

        typeOfMoviesList.addOnButtonCheckedListener { _, checkedId, _ ->
            rvMovie.scrollToPosition(0)
            movieViewModel.changeMoviesList(checkedId)
        }

        swipeToRefresh.setOnRefreshListener {
            if (!checkInternetConnection.isNetworkAvailable()) {
                Toast.makeText(
                    context,
                    getString(R.string.internet_connection_error),
                    Toast.LENGTH_SHORT
                ).show()
                swipeToRefresh.isRefreshing = false
            } else {
                scope.launch {
                    movieViewModel.loadPopularMoviesFromNet()
                    movieViewModel.loadTopRatedMoviesFromNet()
                    movieViewModel.loadUpcomingMoviesFromNet().apply {
                        swipeToRefresh.isRefreshing = false
                    }
                }
            }
        }

        movieViewModel.getPopularMovies()
        movieViewModel.getTopRatedMovies()
        movieViewModel.getUpcomingMovies()

        movieViewModel.popularMoviesList.observe(this) {
            adapter.setMovies(it)
        }

        movieViewModel.getAnotherMovieList().observe(this) { movie ->
            adapter.setMovies(movie)
        }

        return view
    }

    private fun initViews(view: View) {

        typeOfMoviesList = view.findViewById(R.id.toggle_group)
        swipeToRefresh = view.findViewById(R.id.swipe_layout)
        typeOfMoviesList.check(R.id.btn_popular)

        rvMovie = view.findViewById(R.id.movie_list_rv)
        rvMovie.layoutManager = GridLayoutManager(view.context, calculateSpanCount())
        adapter = MoviesAdapter(view.context, clickListener)
        rvMovie.adapter = adapter


        isConnectionErrorFromBundle = arguments?.getBoolean(Constants.CONNECTION_ERROR_ARG) == true
        checkInternetConnection = InternetConnectionManager(context!!)

    }

    private fun initWorkManager() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val updateWorkRequest =
            PeriodicWorkRequest.Builder(MovieDbUpdateWorker::class.java, 8, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(context!!).enqueue(updateWorkRequest)

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

}

