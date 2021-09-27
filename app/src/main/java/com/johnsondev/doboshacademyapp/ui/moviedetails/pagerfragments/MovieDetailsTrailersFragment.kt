package com.johnsondev.doboshacademyapp.ui.moviedetails.pagerfragments

import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MovieTrailersAdapter
import com.johnsondev.doboshacademyapp.databinding.FragmentMovieDetailsTrailersBinding
import com.johnsondev.doboshacademyapp.ui.moviedetails.MovieDetailsViewModel
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragmentBinding
import com.johnsondev.doboshacademyapp.utilities.observeOnce

class MovieDetailsTrailersFragment : BaseFragmentBinding(R.layout.fragment_movie_details_trailers) {

    private val detailsViewModel by viewModels<MovieDetailsViewModel>()
    private val binding by viewBinding(FragmentMovieDetailsTrailersBinding::bind)
    private lateinit var adapter: MovieTrailersAdapter

    override fun initFields() {
        adapter = MovieTrailersAdapter(requireContext())
        binding.rvMovieTrailers.adapter = adapter
    }

    override fun loadData() {}

    override fun bindViews() {}

    override fun initListenersAndObservers() {
        detailsViewModel.getMovieVideos().observeOnce(this, {
            binding.movieTrailersLoadingIndicator.visibility = View.GONE
            adapter.setTrailers(it)
        })
    }
}