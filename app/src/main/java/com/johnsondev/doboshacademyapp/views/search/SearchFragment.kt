package com.johnsondev.doboshacademyapp.views.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorsAdapter
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnActorItemClickListener
import com.johnsondev.doboshacademyapp.adapters.OnMovieItemClickListener
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_MINI
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.hideKeyboard
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.utilities.showKeyboard
import com.johnsondev.doboshacademyapp.viewmodel.SearchViewModel
import com.johnsondev.doboshacademyapp.views.movielist.MoviesListFragmentDirections


class SearchFragment : BaseFragment() {

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
        etSearch = view.findViewById(R.id.search_edit_text)
        moviesResultSpecBtn = view.findViewById(R.id.movies_result_spec_btn)
        tvMoviesCount = view.findViewById(R.id.tv_movies_count)
        rvMoviesResult = view.findViewById(R.id.search_movies_rv)
        actorResultSpecBtn = view.findViewById(R.id.actors_result_spec_btn)
        tvActorCount = view.findViewById(R.id.tv_actors_count)
        rvActorResult = view.findViewById(R.id.search_actors_rv)

        moviesAdapter = MoviesAdapter(requireContext(), onMovieClickListener, false)
        rvMoviesResult.adapter = moviesAdapter

        actorsAdapter = ActorsAdapter(requireContext(), onActorClickListener, ITEM_TYPE_MINI)
        rvActorResult.adapter = actorsAdapter
    }

    override fun layoutId(): Int = R.layout.fragment_search


    override fun loadData() {

    }

    override fun bindViews(view: View) {
//        etSearch.showKeyboard()
    }

    override fun initListenersAndObservers(view: View) {

        etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {

            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchViewModel.searchQuery(etSearch.text.toString())
                    etSearch.hideKeyboard()
                    return true
                }
                return false
            }

        })

        searchViewModel.getMoviesResultList().observeOnce(this, {
            moviesAdapter.setMovies(it)
        })

        searchViewModel.getActorsResultList().observeOnce(this, {
            actorsAdapter.setActors(it)
        })

    }

    override fun onPause() {
        super.onPause()
//        etSearch.hideKeyboard()
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
            val bundleWithActor = Bundle()
            bundleWithActor.putParcelable(Constants.ACTOR_KEY, actor)
            findNavController().navigate(
                R.id.action_searchFragment_to_actorDetailsFragment,
                bundleWithActor
            )
        }
    }

}


