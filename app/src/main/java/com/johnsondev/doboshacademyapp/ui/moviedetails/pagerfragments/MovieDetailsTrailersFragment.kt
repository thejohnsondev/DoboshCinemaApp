package com.johnsondev.doboshacademyapp.ui.moviedetails.pagerfragments

import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MovieTrailersAdapter
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.ui.moviedetails.MovieDetailsViewModel


class MovieDetailsTrailersFragment : BaseFragment() {

    private val detailsViewModel by viewModels<MovieDetailsViewModel>()

    private lateinit var rvTrailers: RecyclerView
    private lateinit var adapter: MovieTrailersAdapter
    private lateinit var loadingIndicator: ProgressBar

    override fun initViews(view: View) {
        loadingIndicator = view.findViewById(R.id.movie_trailers_loading_indicator)
        rvTrailers = view.findViewById(R.id.rv_movie_trailers)
        adapter = MovieTrailersAdapter(requireContext())
        rvTrailers.adapter = adapter
    }

    override fun layoutId(): Int = R.layout.fragment_movie_trailers

    override fun loadData() {

    }

    override fun bindViews(view: View) {

    }

    override fun initListenersAndObservers(view: View) {
        detailsViewModel.getMovieVideos().observeOnce(this, {
            loadingIndicator.visibility = View.GONE
            adapter.setTrailers(it)
        })

    }
}