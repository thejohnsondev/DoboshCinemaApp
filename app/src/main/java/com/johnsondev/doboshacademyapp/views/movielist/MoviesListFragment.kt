package com.johnsondev.doboshacademyapp.views.movielist

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.work.*
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnGenreClickListener
import com.johnsondev.doboshacademyapp.adapters.OnRecyclerItemClicked
import com.johnsondev.doboshacademyapp.adapters.PopGenresAdapter
import com.johnsondev.doboshacademyapp.data.models.Genre
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.services.MovieDbUpdateWorker
import com.johnsondev.doboshacademyapp.utilities.*
import com.johnsondev.doboshacademyapp.utilities.Constants.GENRE_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.GENRE_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.PERIODIC_UPDATE_WORK
import com.johnsondev.doboshacademyapp.utilities.Constants.POPULAR_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.SPECIFIC_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.TOP_RATED_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.UPCOMING_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.viewmodel.MoviesListViewModel
import com.johnsondev.doboshacademyapp.viewmodel.MovieViewModelFactory
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class MoviesListFragment : BaseFragment() {

    private lateinit var rvPopularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var rvTopRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var rvUpcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var rvPopGenres: RecyclerView
    private lateinit var popGenresAdapter: PopGenresAdapter
    private lateinit var popularSpecificListBtn: View
    private lateinit var topRatedSpecificListBtn: View
    private lateinit var upcomingSpecificListBtn: View
    private lateinit var popGenresSpecificListView: View
    private lateinit var tvPopularList: TextView
    private lateinit var tvTopRatedList: TextView
    private lateinit var tvUpcomingList: TextView
    private lateinit var swipeToRefresh: SwipeRefreshLayout
    private lateinit var unavailableListPlaceholder: View
    private lateinit var listViewModel: MoviesListViewModel
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private lateinit var checkInternetConnection: InternetConnectionManager
    private var isConnectionErrorFromBundle: Boolean? = null


    override fun initViews(view: View) {
        listViewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(activity?.application!!)
        )[MoviesListViewModel::class.java]

        swipeToRefresh = view.findViewById(R.id.swipe_layout)
        unavailableListPlaceholder = view.findViewById(R.id.unavailable_list_placeholder)
        popularSpecificListBtn = view.findViewById(R.id.popular_spec_list)
        topRatedSpecificListBtn = view.findViewById(R.id.top_rated_spec_list)
        upcomingSpecificListBtn = view.findViewById(R.id.upcoming_spec_list)
        popGenresSpecificListView = view.findViewById(R.id.pop_genres_spec_list)
        tvPopularList = view.findViewById(R.id.popular_tv)
        tvTopRatedList = view.findViewById(R.id.top_rated_tv)
        tvUpcomingList = view.findViewById(R.id.upcoming_tv)
        tvPopularList.transitionName = POPULAR_SPEC_TYPE
        tvTopRatedList.transitionName = TOP_RATED_SPEC_TYPE
        tvUpcomingList.transitionName = UPCOMING_SPEC_TYPE
        rvPopularMovies = view.findViewById(R.id.popular_movies_rv)
        rvTopRatedMovies = view.findViewById(R.id.top_rated_movies_rv)
        rvUpcomingMovies = view.findViewById(R.id.upcoming_movies_rv)
        rvPopGenres = view.findViewById(R.id.pop_genres_rv)
        popularMoviesAdapter = MoviesAdapter(view.context, movieClickListener, false)
        topRatedMoviesAdapter = MoviesAdapter(view.context, movieClickListener, false)
        upcomingMoviesAdapter = MoviesAdapter(view.context, movieClickListener, false)
        popGenresAdapter = PopGenresAdapter(view.context, genreClickListener)
        rvPopularMovies.adapter = popularMoviesAdapter
        rvTopRatedMovies.adapter = topRatedMoviesAdapter
        rvUpcomingMovies.adapter = upcomingMoviesAdapter
        rvPopGenres.adapter = popGenresAdapter
        checkInternetConnection = InternetConnectionManager(requireContext())
        initWorkManager()

    }

    override fun layoutId(): Int = R.layout.fragment_movies_list

    override fun loadData() {
        isConnectionErrorFromBundle = arguments?.getBoolean(Constants.CONNECTION_ERROR_ARG) == true

        listViewModel.apply {
            getPopularMovies()
            getTopRatedMovies()
            getUpcomingMovies()
            loadGenresList()
        }
    }

    override fun bindViews(view: View) {}

    override fun initListenersAndObservers(view: View) {
        if (isConnectionErrorFromBundle == true && !listViewModel.isInternetConnectionAvailable()) {
            showMessage(getString(R.string.internet_connection_error))
        }

        swipeToRefresh.setOnRefreshListener {
            if (!checkInternetConnection.isNetworkAvailable()) {
                showMessage(getString(R.string.internet_connection_error))
                swipeToRefresh.isRefreshing = false
            } else {
                scope.launch {
                    listViewModel.loadMoviesFromNet().apply {
                        swipeToRefresh.isRefreshing = false
                        withContext(Dispatchers.Main) {
                            unavailableListPlaceholder.visibility = View.GONE
                            popularSpecificListBtn.visibility = View.VISIBLE
                            topRatedSpecificListBtn.visibility = View.VISIBLE
                            upcomingSpecificListBtn.visibility = View.VISIBLE
                            popGenresSpecificListView.visibility = View.VISIBLE
                            rvPopularMovies.visibility = View.VISIBLE
                            rvTopRatedMovies.visibility = View.VISIBLE
                            rvUpcomingMovies.visibility = View.VISIBLE
                            rvPopGenres.visibility = View.VISIBLE
                        }
                    }
                }

            }
        }

        popularSpecificListBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(SPECIFIC_LIST_TYPE, POPULAR_SPEC_TYPE)
            findNavController().navigate(
                R.id.action_moviesListFragment_to_specificListFragment,
                bundle
            )
        }

        topRatedSpecificListBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(SPECIFIC_LIST_TYPE, TOP_RATED_SPEC_TYPE)
            findNavController().navigate(
                R.id.action_moviesListFragment_to_specificListFragment,
                bundle
            )
        }

        upcomingSpecificListBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(SPECIFIC_LIST_TYPE, UPCOMING_SPEC_TYPE)

            findNavController().navigate(
                R.id.action_moviesListFragment_to_specificListFragment,
                bundle
            )
        }

        listViewModel.popularMoviesList.observe(viewLifecycleOwner) {
            popularMoviesAdapter.setMovies(it)
        }

        listViewModel.topRatedMoviesList.observe(viewLifecycleOwner) {
            topRatedMoviesAdapter.setMovies(it)
        }

        listViewModel.upcomingMoviesList.observe(viewLifecycleOwner) {
            upcomingMoviesAdapter.setMovies(it)
        }

        listViewModel.getGenresList().observe(viewLifecycleOwner) {
            popGenresAdapter.setGenresList(it)
        }

        listViewModel.error.observe(viewLifecycleOwner) {
            if (it != null) {
                onError(it)
                unavailableListPlaceholder.visibility = View.VISIBLE
                popularSpecificListBtn.visibility = View.GONE
                topRatedSpecificListBtn.visibility = View.GONE
                upcomingSpecificListBtn.visibility = View.GONE
                popGenresSpecificListView.visibility = View.GONE
                rvPopularMovies.visibility = View.GONE
                rvTopRatedMovies.visibility = View.GONE
                rvUpcomingMovies.visibility = View.GONE
                rvPopGenres.visibility = View.GONE
            }
        }

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

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            PERIODIC_UPDATE_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            updateWorkRequest
        )

    }

    private val movieClickListener = object : OnRecyclerItemClicked {
        override fun onClick(movie: Movie) {
            val bundleWithMovie = Bundle()
            bundleWithMovie.putInt(MOVIE_KEY, movie.id)
            findNavController().navigate(
                R.id.action_moviesListFragment_to_moviesDetailsFragment,
                bundleWithMovie
            )
        }
    }

    private val genreClickListener = object : OnGenreClickListener {
        override fun onClick(genre: Genre) {
            val bundleWithGenre = Bundle()
            bundleWithGenre.putParcelable(GENRE_KEY, genre)
            bundleWithGenre.putString(SPECIFIC_LIST_TYPE, GENRE_SPEC_TYPE)
            findNavController().navigate(
                R.id.action_moviesListFragment_to_specificListFragment,
                bundleWithGenre
            )

        }
    }


}

