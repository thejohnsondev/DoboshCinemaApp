package com.johnsondev.doboshacademyapp.views.movietrailers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.MovieTrailersAdapter
import com.johnsondev.doboshacademyapp.data.network.dto.MovieVideoDto


class MovieTrailersFragment : Fragment() {

    private lateinit var rvTrailers: RecyclerView
    private lateinit var backBtn: View
    private lateinit var adapter: MovieTrailersAdapter

    private lateinit var trailersList: ArrayList<MovieVideoDto>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_trailers, container, false)

        initViews(view)
        initListeners()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun initViews(view: View) {

        rvTrailers = view.findViewById(R.id.rv_movie_trailers)
        backBtn = view.findViewById(R.id.back_to_movie_details_btn)
        adapter = MovieTrailersAdapter(requireContext())
        rvTrailers.adapter = adapter

        trailersList = arguments?.getParcelableArrayList("TRAILER_KEY") ?: arrayListOf()
        adapter.setTrailers(trailersList)

    }

    private fun initListeners(){

        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }




}