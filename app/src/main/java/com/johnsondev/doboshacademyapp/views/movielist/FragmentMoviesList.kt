package com.johnsondev.doboshacademyapp.views.movielist

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.work.*
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialSharedAxis
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnRecyclerItemClicked
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.services.MovieDbUpdateWorker
import com.johnsondev.doboshacademyapp.utilities.InternetConnectionManager
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.HORIZONTAL_SPAN_COUNT
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.PERIODIC_UPDATE_WORK
import com.johnsondev.doboshacademyapp.utilities.Constants.POPULAR_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.SPECIFIC_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.TOP_RATED_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.UPCOMING_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.VERTICAL_SPAN_COUNT
import com.johnsondev.doboshacademyapp.utilities.getUpdateTime
import com.johnsondev.doboshacademyapp.utilities.saveUpdateTime
import com.johnsondev.doboshacademyapp.views.moviedetails.FragmentMoviesDetails
import com.johnsondev.doboshacademyapp.viewmodel.MoviesListViewModel
import com.johnsondev.doboshacademyapp.viewmodel.MovieViewModelFactory
import com.johnsondev.doboshacademyapp.views.specificlist.SpecificListFragment
import kotlinx.coroutines.*
import org.w3c.dom.Text
import java.util.concurrent.TimeUnit

class FragmentMoviesList : Fragment() {

    private lateinit var rvPopularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var rvTopRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var rvUpcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter

    private lateinit var popularSpecificListBtn: View
    private lateinit var topRatedSpecificListBtn: View
    private lateinit var upcomingSpecificListBtn: View
    private lateinit var tvPopularList: TextView
    private lateinit var tvTopRatedList: TextView
    private lateinit var tvUpcomingList: TextView
    private lateinit var swipeToRefresh: SwipeRefreshLayout
    private lateinit var listViewModel: MoviesListViewModel
    private lateinit var tvLastUpdateTime: TextView
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private lateinit var checkInternetConnection: InternetConnectionManager
    private var isConnectionErrorFromBundle: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        listViewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(activity?.application!!)
        )[MoviesListViewModel::class.java]

        initViews(view)
        initWorkManager()
        initListenersAndObservers(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }


    private fun initViews(view: View) {

        tvLastUpdateTime = view.findViewById(R.id.last_update_tv)
        swipeToRefresh = view.findViewById(R.id.swipe_layout)

        popularSpecificListBtn = view.findViewById(R.id.popular_spec_list)
        topRatedSpecificListBtn = view.findViewById(R.id.top_rated_spec_list)
        upcomingSpecificListBtn = view.findViewById(R.id.upcoming_spec_list)

        tvPopularList = view.findViewById(R.id.popular_tv)
        tvTopRatedList = view.findViewById(R.id.top_rated_tv)
        tvUpcomingList = view.findViewById(R.id.upcoming_tv)

        tvPopularList.transitionName = POPULAR_SPEC_TYPE
        tvTopRatedList.transitionName = TOP_RATED_SPEC_TYPE
        tvUpcomingList.transitionName = UPCOMING_SPEC_TYPE


        rvPopularMovies = view.findViewById(R.id.popular_movies_rv)
        rvTopRatedMovies = view.findViewById(R.id.top_rated_movies_rv)
        rvUpcomingMovies = view.findViewById(R.id.upcoming_movies_rv)
        popularMoviesAdapter = MoviesAdapter(view.context, clickListener)
        topRatedMoviesAdapter = MoviesAdapter(view.context, clickListener)
        upcomingMoviesAdapter = MoviesAdapter(view.context, clickListener)
        rvPopularMovies.adapter = popularMoviesAdapter
        rvTopRatedMovies.adapter = topRatedMoviesAdapter
        rvUpcomingMovies.adapter = upcomingMoviesAdapter

        isConnectionErrorFromBundle = arguments?.getBoolean(Constants.CONNECTION_ERROR_ARG) == true
        checkInternetConnection = InternetConnectionManager(requireContext())

    }

    private fun initWorkManager() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
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

//        it`s for tests
//        WorkManager.getInstance(requireContext()).enqueue(updateWorkRequest)

    }

    private fun initListenersAndObservers(view: View) {

        if (isConnectionErrorFromBundle == true && !listViewModel.isInternetConnectionAvailable()) {
            Toast.makeText(
                context,
                getString(R.string.internet_connection_error),
                Toast.LENGTH_SHORT
            ).show()
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
                    listViewModel.loadMoviesFromNet().apply {
                        swipeToRefresh.isRefreshing = false
                        saveUpdateTime(requireContext())
                        tvLastUpdateTime.text =
                            view.context.getString(
                                R.string.last_update,
                                getUpdateTime(requireContext())
                            )
                        rvPopularMovies.scrollToPosition(0)
                        rvTopRatedMovies.scrollToPosition(0)
                        rvUpcomingMovies.scrollToPosition(0)
                    }
                }

            }
        }

        popularSpecificListBtn.setOnClickListener {
            openSpecificFragment(POPULAR_SPEC_TYPE)
        }

        topRatedSpecificListBtn.setOnClickListener {
            openSpecificFragment(TOP_RATED_SPEC_TYPE)
        }

        upcomingSpecificListBtn.setOnClickListener {
            openSpecificFragment(UPCOMING_SPEC_TYPE)
        }

        listViewModel.getPopularMovies()
        listViewModel.getTopRatedMovies()
        listViewModel.getUpcomingMovies()


        listViewModel.popularMoviesList.observe(viewLifecycleOwner) {
            popularMoviesAdapter.setMovies(it)
        }

        listViewModel.topRatedMoviesList.observe(viewLifecycleOwner) {
            topRatedMoviesAdapter.setMovies(it)
        }

        listViewModel.upcomingMoviesList.observe(viewLifecycleOwner) { movie ->
            upcomingMoviesAdapter.setMovies(movie)
        }

        listViewModel.getLastUpdateTime(requireContext()).observe(viewLifecycleOwner) { time ->
            tvLastUpdateTime.text = view.context.getString(R.string.last_update, time)
        }
    }

    private fun openSpecificFragment(type: String){
        val bundle = Bundle()
        bundle.putString(SPECIFIC_LIST_TYPE, type)
        val specificListFragment = SpecificListFragment().apply {
            arguments = bundle
        }

        parentFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            addToBackStack(null)
            replace(R.id.main_container, specificListFragment)
            commit()
        }
    }

    private fun calculateSpanCount(): Int {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            VERTICAL_SPAN_COUNT else HORIZONTAL_SPAN_COUNT
    }

    private fun doOnClick(movie: Movie, view: View) {

        val bundleWithMovie = Bundle()
        bundleWithMovie.putParcelable(MOVIE_KEY, movie)

        val fragmentMoviesDetails = FragmentMoviesDetails()
        fragmentMoviesDetails.arguments = bundleWithMovie

        rvPopularMovies.let {
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

    private val clickListener = object : OnRecyclerItemClicked {
        override fun onClick(movie: Movie, view: View) {
            doOnClick(movie, view)
        }
    }

}

