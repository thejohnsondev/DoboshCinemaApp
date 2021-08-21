package com.johnsondev.doboshacademyapp.views.movietrailers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MovieTrailersAdapter
import com.johnsondev.doboshacademyapp.data.network.dto.MovieVideoDto
import com.johnsondev.doboshacademyapp.utilities.Constants.TRAILERS_KEY
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment


class MovieTrailersFragment : BaseFragment() {

    private lateinit var rvTrailers: RecyclerView
    private lateinit var backBtn: View
    private lateinit var adapter: MovieTrailersAdapter
    private lateinit var trailersList: ArrayList<MovieVideoDto>

    override fun initViews(view: View) {
        rvTrailers = view.findViewById(R.id.rv_movie_trailers)
        backBtn = view.findViewById(R.id.back_to_movie_details_btn)
        adapter = MovieTrailersAdapter(requireContext())
        rvTrailers.adapter = adapter
    }

    override fun layoutId(): Int = R.layout.fragment_movie_trailers

    override fun loadData() {
        trailersList = arguments?.getParcelableArrayList(TRAILERS_KEY) ?: arrayListOf()
    }

    override fun bindViews(view: View) {
        adapter.setTrailers(trailersList)
    }

    override fun initListenersAndObservers(view: View) {
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}