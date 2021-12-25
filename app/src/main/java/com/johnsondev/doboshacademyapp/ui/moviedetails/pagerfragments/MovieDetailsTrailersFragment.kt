package com.johnsondev.doboshacademyapp.ui.moviedetails.pagerfragments

import android.view.View
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MovieTrailersAdapter
import com.johnsondev.doboshacademyapp.databinding.FragmentMovieDetailsTrailersBinding
import com.johnsondev.doboshacademyapp.ui.moviedetails.MovieDetailsViewModel
import com.johnsondev.doboshacademyapp.ui.moviedetails.MovieDetailsViewModelFactory
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MovieDetailsTrailersFragment : BaseFragment(R.layout.fragment_movie_details_trailers),
    KodeinAware {

    override val kodein by kodein()

    private val factory: MovieDetailsViewModelFactory by instance()
    private val detailsViewModel: MovieDetailsViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[MovieDetailsViewModel::class.java]
    }

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