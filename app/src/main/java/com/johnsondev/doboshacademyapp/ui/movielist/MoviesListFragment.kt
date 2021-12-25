package com.johnsondev.doboshacademyapp.ui.movielist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.work.*
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.*
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.data.models.base.Genre
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.data.services.MovieDbUpdateWorker
import com.johnsondev.doboshacademyapp.databinding.FragmentMoviesListBinding
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.GENRE_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.GENRE_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_MINI
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ITEM_DEFAULT
import com.johnsondev.doboshacademyapp.utilities.Constants.PERIODIC_UPDATE_WORK
import com.johnsondev.doboshacademyapp.utilities.Constants.POPULAR_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.POP_ACTORS_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.SPECIFIC_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.TOP_RATED_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.UPCOMING_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.showMessage
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

class MoviesListFragment : BaseFragment(R.layout.fragment_movies_list), KodeinAware {

    override val kodein by kodein()

    private val factory: MoviesListViewModelFactory by instance()
    private val listViewModel: MoviesListViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[MoviesListViewModel::class.java]
    }

    private val binding by viewBinding(FragmentMoviesListBinding::bind)
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private var isConnectionErrorFromBundle: Boolean? = null
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var popGenresAdapter: GenresAdapter
    private lateinit var popActorsAdapter: ActorsAdapter


    override fun initFields() {
        with(binding) {
            popularMoviesAdapter =
                MoviesAdapter(requireContext(), movieClickListener, MOVIE_ITEM_DEFAULT)
            topRatedMoviesAdapter =
                MoviesAdapter(requireContext(), movieClickListener, MOVIE_ITEM_DEFAULT)
            upcomingMoviesAdapter =
                MoviesAdapter(requireContext(), movieClickListener, MOVIE_ITEM_DEFAULT)
            popGenresAdapter = GenresAdapter(requireContext(), genreClickListener)
            popActorsAdapter = ActorsAdapter(requireContext(), actorClickListener, ITEM_TYPE_MINI)
            popularMoviesRv.adapter = popularMoviesAdapter
            topRatedMoviesRv.adapter = topRatedMoviesAdapter
            upcomingMoviesRv.adapter = upcomingMoviesAdapter
            popGenresRv.adapter = popGenresAdapter
            popActorsRv.adapter = popActorsAdapter
            initWorkManager()
        }
    }

    override fun loadData() {
        isConnectionErrorFromBundle = arguments?.getBoolean(Constants.CONNECTION_ERROR_ARG) == true
        listViewModel.apply {
            getPopularMovies()
            getTopRatedMovies()
            getUpcomingMovies()
            loadGenresList()
            loadPopularActors()
        }
    }

    override fun bindViews() {}

    override fun initListenersAndObservers() {
        with(binding) {

            if (isConnectionErrorFromBundle == true && !listViewModel.isInternetConnectionAvailable()) {
                showMessage(getString(R.string.internet_connection_error))
            }

            swipeLayout.setOnRefreshListener {
                if (!listViewModel.isInternetConnectionAvailable()) {
                    showMessage(getString(R.string.internet_connection_error))
                    swipeLayout.isRefreshing = false
                } else {
                    scope.launch {
                        listViewModel.loadGenresList()
                        listViewModel.loadPopularActors()
                        listViewModel.loadMoviesFromNet().apply {
                            swipeLayout.isRefreshing = false
                            withContext(Dispatchers.Main) {
                                showViews()
                            }
                        }
                    }
                }
            }

            searchBoxBtn.setOnClickListener {
                findNavController().navigate(R.id.action_moviesListFragment_to_searchFragment)
            }

            popularSpecList.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(SPECIFIC_LIST_TYPE, POPULAR_SPEC_TYPE)
                findNavController().navigate(
                    R.id.action_moviesListFragment_to_specificListFragment,
                    bundle
                )
            }

            topRatedSpecList.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(SPECIFIC_LIST_TYPE, TOP_RATED_SPEC_TYPE)
                findNavController().navigate(
                    R.id.action_moviesListFragment_to_specificListFragment,
                    bundle
                )
            }

            upcomingSpecList.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(SPECIFIC_LIST_TYPE, UPCOMING_SPEC_TYPE)
                findNavController().navigate(
                    R.id.action_moviesListFragment_to_specificListFragment,
                    bundle
                )
            }

            popActorsSpecList.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(SPECIFIC_LIST_TYPE, POP_ACTORS_SPEC_TYPE)
                findNavController().navigate(
                    R.id.action_moviesListFragment_to_specificActorsListFragment,
                    bundle
                )
            }

            listViewModel.popularMoviesList.observe(viewLifecycleOwner) {
                popularMoviesAdapter.setMovies(it)
                popularListLoadingIndicator.visibility = View.GONE
            }

            listViewModel.topRatedMoviesList.observe(viewLifecycleOwner) {
                topRatedMoviesAdapter.setMovies(it)
                topListLoadingIndicator.visibility = View.GONE
            }

            listViewModel.upcomingMoviesList.observe(viewLifecycleOwner) {
                upcomingMoviesAdapter.setMovies(it)
                upcomingListLoadingIndicator.visibility = View.GONE
            }

            listViewModel.getGenresList().observe(viewLifecycleOwner) {
                popGenresAdapter.setGenresList(it)
            }

            listViewModel.getPopularActors().observe(viewLifecycleOwner) {
                popActorsAdapter.setActors(it)
            }

            listViewModel.error.observe(viewLifecycleOwner) {
                if (it != null) {
                    onError(it)
                    hideViews()
                }
            }
        }
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

    }

    private fun hideViews() {
        with(binding) {
            unavailableListPlaceholder.root.visibility = View.VISIBLE
            popularSpecList.visibility = View.GONE
            topRatedSpecList.visibility = View.GONE
            upcomingSpecList.visibility = View.GONE
            popGenresSpecList.visibility = View.GONE
            popActorsSpecList.visibility = View.GONE
            popularMoviesRv.visibility = View.GONE
            topRatedMoviesRv.visibility = View.GONE
            upcomingMoviesRv.visibility = View.GONE
            popGenresRv.visibility = View.GONE
            popActorsRv.visibility = View.GONE
        }
    }

    private fun showViews() {
        with(binding) {
            unavailableListPlaceholder.root.visibility = View.GONE
            popularSpecList.visibility = View.VISIBLE
            topRatedSpecList.visibility = View.VISIBLE
            upcomingSpecList.visibility = View.VISIBLE
            popGenresSpecList.visibility = View.VISIBLE
            popActorsSpecList.visibility = View.VISIBLE
            popularMoviesRv.visibility = View.VISIBLE
            topRatedMoviesRv.visibility = View.VISIBLE
            upcomingMoviesRv.visibility = View.VISIBLE
            popGenresRv.visibility = View.VISIBLE
            popActorsRv.visibility = View.VISIBLE
        }
    }

    private val movieClickListener = object : OnMovieItemClickListener {
        override fun onClick(movie: Movie) {
            findNavController().navigate(
                MoviesListFragmentDirections.actionMoviesListFragmentToDetailsActivity(movie.id)
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

    private val actorClickListener = object : OnActorItemClickListener {
        override fun onClick(actor: Actor) {
            findNavController().navigate(
                MoviesListFragmentDirections.actionMoviesListFragmentToActorDetailsActivity2(actor.id)
            )
        }
    }
}