package com.johnsondev.doboshacademyapp.ui.moviedetails.pagerfragments

import android.content.Context
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MovieTrailersAdapter
import com.johnsondev.doboshacademyapp.databinding.FragmentMovieDetailsTrailersBinding
import com.johnsondev.doboshacademyapp.ui.moviedetails.MovieDetailsViewModel
import com.johnsondev.doboshacademyapp.utilities.appComponent
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import javax.inject.Inject

class MovieDetailsTrailersFragment : BaseFragment(R.layout.fragment_movie_details_trailers) {

    @Inject
    lateinit var factory: MovieDetailsViewModel.Factory
    private val viewModel: MovieDetailsViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[MovieDetailsViewModel::class.java]
    }
    private val binding by viewBinding(FragmentMovieDetailsTrailersBinding::bind)
    private lateinit var adapter: MovieTrailersAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent().inject(this)
    }

    override fun initFields() {
        adapter = MovieTrailersAdapter(requireContext())
        binding.rvMovieTrailers.adapter = adapter
    }

    override fun loadData() {}

    override fun bindViews() {}

    override fun initListenersAndObservers() {
        viewModel.getMovieVideos().observeOnce(this, {
            binding.movieTrailersLoadingIndicator.visibility = View.GONE
            adapter.setTrailers(it)
        })
    }
}