package com.johnsondev.doboshacademyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentMoviesDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)

        val backBtn : TextView = view.findViewById(R.id.back_btn)
        backBtn.setOnClickListener(){
            fragmentManager?.popBackStack()
        }

        return view
    }

}