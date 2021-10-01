package com.johnsondev.doboshacademyapp.ui.search

import android.os.Bundle
import android.os.Parcelable
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorsAdapter
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnActorItemClickListener
import com.johnsondev.doboshacademyapp.adapters.OnMovieItemClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.databinding.FragmentSearchBinding
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import java.util.*


class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private val searchViewModel by viewModels<SearchViewModel>()
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var actorsAdapter: ActorsAdapter

    override fun initFields() {
        moviesAdapter = MoviesAdapter(requireContext(), onMovieClickListener, MOVIE_ITEM_MINI)
        binding.searchMoviesRv.adapter = moviesAdapter

        actorsAdapter = ActorsAdapter(requireContext(), onActorClickListener, ITEM_TYPE_MINI)
        binding.searchActorsRv.adapter = actorsAdapter
    }

    override fun loadData() {}

    override fun bindViews() {}


    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun initListenersAndObservers() {
        with(binding) {
            searchEditText.setOnEditorActionListener(object : TextView.OnEditorActionListener {

                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        lifecycleScope.launch {
                            searchViewModel.queryChannel.send(searchEditText.text.toString())
                        }
                        searchEditText.hideKeyboard()
                        return true
                    }
                    return false
                }

            })

            searchEditText.afterTextChanged {
                lifecycleScope.launch {
                    searchViewModel.queryChannel.send(searchEditText.text.toString())
                }
            }

            searchViewModel.searchResultMap.observeOnce(
                viewLifecycleOwner,
                { handleSearchResult(it) })
            searchViewModel.searchState.observeOnce(viewLifecycleOwner, { handleSearchState(it) })

            moviesResultSpecBtn.setOnClickListener {
                if (searchViewModel.searchResultMap.value is ValidSearchResult) {
                    val bundle = Bundle()
                    bundle.putParcelableArrayList(
                        MOVIES_SEARCH_RESULT_SPEC_TYPE,
                        (searchViewModel.searchResultMap.value as ValidSearchResult).resultLists.movies as ArrayList<out Parcelable>
                    )
                    bundle.putString(SPECIFIC_LIST_TYPE, SEARCH_RESULT_SPEC_TYPE)
                    findNavController().navigate(
                        R.id.action_searchFragment_to_specificListFragment,
                        bundle
                    )
                }
            }

            actorsResultSpecBtn.setOnClickListener {
                if (searchViewModel.searchResultMap.value is ValidSearchResult) {
                    val bundle = Bundle()
                    bundle.putParcelableArrayList(
                        ACTORS_SEARCH_RESULT_SPEC_TYPE,
                        (searchViewModel.searchResultMap.value as ValidSearchResult).resultLists.actors as ArrayList<out Parcelable>
                    )
                    bundle.putString(SPECIFIC_LIST_TYPE, SEARCH_RESULT_SPEC_TYPE)
                    findNavController().navigate(
                        R.id.action_searchFragment_to_specificActorsListFragment,
                        bundle
                    )
                }

            }
        }
    }


    private fun handleSearchResult(result: SearchResult) {
        with(binding) {
            when (result) {
                is ValidSearchResult -> {
                    if (result.resultLists.movies.isEmpty()) {
                        findSomethingPlaceholder.root.visibility = View.GONE
                        nothingFoundPlaceholder.root.visibility = View.GONE
                        moviesAdapter.setMovies(emptyList())
                        moviesResultSpecBtn.visibility = View.GONE
                    }
                    if (result.resultLists.actors.isEmpty()) {
                        findSomethingPlaceholder.root.visibility = View.GONE
                        nothingFoundPlaceholder.root.visibility = View.GONE
                        actorsAdapter.setActors(emptyList())
                        actorsResultSpecBtn.visibility = View.GONE
                    }
                    if (result.resultLists.movies.isNotEmpty()) {
                        nothingFoundPlaceholder.root.visibility = View.GONE
                        findSomethingPlaceholder.root.visibility = View.GONE
                        moviesResultSpecBtn.visibility = View.VISIBLE
                        moviesAdapter.setMovies(result.resultLists.movies)
                        tvMoviesCount.text = result.resultLists.movies.size.toString()
                    }
                    if (result.resultLists.actors.isNotEmpty()) {
                        nothingFoundPlaceholder.root.visibility = View.GONE
                        findSomethingPlaceholder.root.visibility = View.GONE
                        actorsResultSpecBtn.visibility = View.VISIBLE
                        actorsAdapter.setActors(result.resultLists.actors)
                        tvActorsCount.text = result.resultLists.actors.size.toString()
                    }
                }
                is ErrorSearchResult -> {
                    nothingFoundPlaceholder.root.visibility = View.VISIBLE
                    findSomethingPlaceholder.root.visibility = View.GONE
                    moviesAdapter.setMovies(emptyList())
                    actorsAdapter.setActors(emptyList())
                    moviesResultSpecBtn.visibility = View.GONE
                }
                is EmptySearchResult -> {
                    nothingFoundPlaceholder.root.visibility = View.VISIBLE
                    findSomethingPlaceholder.root.visibility = View.GONE
                    moviesResultSpecBtn.visibility = View.GONE
                    actorsResultSpecBtn.visibility = View.GONE
                }
                is EmptyQuery -> {
                    nothingFoundPlaceholder.root.visibility = View.GONE
                    findSomethingPlaceholder.root.visibility = View.VISIBLE
                    moviesAdapter.setMovies(emptyList())
                    actorsAdapter.setActors(emptyList())
                    moviesResultSpecBtn.visibility = View.GONE
                    actorsResultSpecBtn.visibility = View.GONE
                }
                is TerminalSearchError -> {
                    showMessage(getString(R.string.error_on_loading))
                }
            }
        }
    }

    private fun handleSearchState(state: LoadingState) {
        when (state) {
            is Loading -> {
                binding.searchProgress.visibility = View.VISIBLE
            }
            is Ready -> {
                binding.searchProgress.visibility = View.GONE
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


