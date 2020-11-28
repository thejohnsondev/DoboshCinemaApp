package com.johnsondev.doboshacademyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class FragmentMoviesList : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        view.findViewById<View>(R.id.movie_card).apply {
            setOnClickListener {
                fragmentManager?.beginTransaction()?.apply {
                    setCustomAnimations(
                       R.anim.slide_in,
                       R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    replace(R.id.main_container, FragmentMoviesDetails())
                    addToBackStack(null)
                    commit()
                }
            }
        }
        return view
    }
}