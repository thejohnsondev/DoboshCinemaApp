package com.johnsondev.doboshacademyapp.views.search

import android.os.Bundle
import android.os.Parcelable
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorsAdapter
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnActorItemClickListener
import com.johnsondev.doboshacademyapp.adapters.OnMovieItemClickListener
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.utilities.Constants.ACTORS_SEARCH_RESULT_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_MINI
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIES_SEARCH_RESULT_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ITEM_MINI
import com.johnsondev.doboshacademyapp.utilities.Constants.SEARCH_RESULT_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.SPECIFIC_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.afterTextChanged
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.hideKeyboard
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.utilities.showMessage
import com.johnsondev.doboshacademyapp.utilities.states.*
import com.johnsondev.doboshacademyapp.viewmodel.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import java.util.*


class SearchFragment : BaseFragment() {

    private lateinit var nothingFoundPlaceholder: View
    private lateinit var findSomethingPlaceholder: View
    private lateinit var searchLoadingIndicator: ProgressBar
    private lateinit var etSearch: EditText
    private lateinit var moviesResultSpecBtn: View
    private lateinit var tvMoviesCount: TextView
    private lateinit var rvMoviesResult: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var actorResultSpecBtn: View
    private lateinit var tvActorCount: TextView
    private lateinit var rvActorResult: RecyclerView
    private lateinit var actorsAdapter: ActorsAdapter


    private val searchViewModel by viewModels<SearchViewModel>()

    override fun initViews(view: View) {
        nothingFoundPlaceholder = view.findViewById(R.id.nothing_found_placeholder)
        findSomethingPlaceholder = view.findViewById(R.id.find_something_placeholder)
        searchLoadingIndicator = view.findViewById(R.id.search_progress)
        etSearch = view.findViewById(R.id.search_edit_text)
        moviesResultSpecBtn = view.findViewById(R.id.movies_result_spec_btn)
        tvMoviesCount = view.findViewById(R.id.tv_movies_count)
        rvMoviesResult = view.findViewById(R.id.search_movies_rv)
        actorResultSpecBtn = view.findViewById(R.id.actors_result_spec_btn)
        tvActorCount = view.findViewById(R.id.tv_actors_count)
        rvActorResult = view.findViewById(R.id.search_actors_rv)

        moviesAdapter = MoviesAdapter(requireContext(), onMovieClickListener, MOVIE_ITEM_MINI)
        rvMoviesResult.adapter = moviesAdapter

        actorsAdapter = ActorsAdapter(requireContext(), onActorClickListener, ITEM_TYPE_MINI)
        rvActorResult.adapter = actorsAdapter


    }

    override fun layoutId(): Int = R.layout.fragment_search


    override fun loadData() {}

    override fun bindViews(view: View) {}


    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun initListenersAndObservers(view: View) {

        etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {

            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    lifecycleScope.launch {
                        searchViewModel.queryChannel.send(etSearch.text.toString())
                    }
                    etSearch.hideKeyboard()
                    return true
                }
                return false
            }

        })

        etSearch.afterTextChanged {
            lifecycleScope.launch {
                searchViewModel.queryChannel.send(etSearch.text.toString())
            }
        }

        searchViewModel.searchResultMap.observeOnce(viewLifecycleOwner, { handleSearchResult(it) })
        searchViewModel.searchState.observeOnce(viewLifecycleOwner, { handleSearchState(it) })

        moviesResultSpecBtn.setOnClickListener {
            if (searchViewModel.searchResultMap.value is ValidResult) {
                val bundle = Bundle()
                bundle.putParcelableArrayList(
                    MOVIES_SEARCH_RESULT_SPEC_TYPE,
                    (searchViewModel.searchResultMap.value as ValidResult).resultLists.movies as ArrayList<out Parcelable>
                )
                bundle.putString(SPECIFIC_LIST_TYPE, SEARCH_RESULT_SPEC_TYPE)
                findNavController().navigate(
                    R.id.action_searchFragment_to_specificListFragment,
                    bundle
                )
            }
        }

        actorResultSpecBtn.setOnClickListener {
            if (searchViewModel.searchResultMap.value is ValidResult) {
                val bundle = Bundle()
                bundle.putParcelableArrayList(
                    ACTORS_SEARCH_RESULT_SPEC_TYPE,
                    (searchViewModel.searchResultMap.value as ValidResult).resultLists.actors as ArrayList<out Parcelable>
                )
                bundle.putString(SPECIFIC_LIST_TYPE, SEARCH_RESULT_SPEC_TYPE)
                findNavController().navigate(
                    R.id.action_searchFragment_to_specificActorsListFragment,
                    bundle
                )
            }

        }
    }


    private fun handleSearchResult(result: SearchResult) {
        when (result) {
            is ValidResult -> {
                if (result.resultLists.movies.isEmpty()) {
                    findSomethingPlaceholder.visibility = View.GONE
                    nothingFoundPlaceholder.visibility = View.GONE
                    moviesAdapter.setMovies(emptyList())
                    moviesResultSpecBtn.visibility = View.GONE
                }
                if (result.resultLists.actors.isEmpty()) {
                    findSomethingPlaceholder.visibility = View.GONE
                    nothingFoundPlaceholder.visibility = View.GONE
                    actorsAdapter.setActors(emptyList())
                    actorResultSpecBtn.visibility = View.GONE
                }
                if (result.resultLists.movies.isNotEmpty()) {
                    nothingFoundPlaceholder.visibility = View.GONE
                    findSomethingPlaceholder.visibility = View.GONE
                    moviesResultSpecBtn.visibility = View.VISIBLE
                    moviesAdapter.setMovies(result.resultLists.movies)
                    tvMoviesCount.text = result.resultLists.movies.size.toString()
                }
                if (result.resultLists.actors.isNotEmpty()) {
                    nothingFoundPlaceholder.visibility = View.GONE
                    findSomethingPlaceholder.visibility = View.GONE
                    actorResultSpecBtn.visibility = View.VISIBLE
                    actorsAdapter.setActors(result.resultLists.actors)
                    tvActorCount.text = result.resultLists.actors.size.toString()
                }
            }
            is ErrorResult -> {
                nothingFoundPlaceholder.visibility = View.VISIBLE
                findSomethingPlaceholder.visibility = View.GONE
                moviesAdapter.setMovies(emptyList())
                actorsAdapter.setActors(emptyList())
                moviesResultSpecBtn.visibility = View.GONE
            }
            is EmptyResult -> {
                nothingFoundPlaceholder.visibility = View.VISIBLE
                findSomethingPlaceholder.visibility = View.GONE
                moviesResultSpecBtn.visibility = View.GONE
                actorResultSpecBtn.visibility = View.GONE
            }
            is EmptyQuery -> {
                nothingFoundPlaceholder.visibility = View.GONE
                findSomethingPlaceholder.visibility = View.VISIBLE
                moviesAdapter.setMovies(emptyList())
                actorsAdapter.setActors(emptyList())
                moviesResultSpecBtn.visibility = View.GONE
                actorResultSpecBtn.visibility = View.GONE
            }
            is TerminalError -> {
                showMessage(getString(R.string.error_on_loading))
            }
        }
    }

    private fun handleSearchState(state: SearchState) {
        when (state) {
            is Loading -> {
                searchLoadingIndicator.visibility = View.VISIBLE
            }
            is Ready -> {
                searchLoadingIndicator.visibility = View.GONE
            }
        }
    }


    private val onMovieClickListener = object : OnMovieItemClickListener {
        override fun onClick(movie: Movie) {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailsActivity(movie.id)
            )
        }
    }

    private val onActorClickListener = object : OnActorItemClickListener {
        override fun onClick(actor: Actor) {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToActorDetailsActivity2(actor.id)
            )
        }
    }


}


