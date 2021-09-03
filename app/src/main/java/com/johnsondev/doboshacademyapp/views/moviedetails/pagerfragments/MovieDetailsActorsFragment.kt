package com.johnsondev.doboshacademyapp.views.moviedetails.pagerfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnsondev.doboshacademyapp.R

class MovieDetailsActorsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_movie_details_actors, container, false)
        // Inflate the layout for this fragment
        return view
    }


}