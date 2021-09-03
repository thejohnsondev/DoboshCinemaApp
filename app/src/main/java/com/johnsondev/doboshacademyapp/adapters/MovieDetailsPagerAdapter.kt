package com.johnsondev.doboshacademyapp.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.johnsondev.doboshacademyapp.views.moviedetails.pagerfragments.MovieDetailsActorsFragment
import com.johnsondev.doboshacademyapp.views.moviedetails.pagerfragments.MovieDetailsInfoFragment

class MovieDetailsPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> MovieDetailsInfoFragment()
            1 -> MovieDetailsActorsFragment()
            else -> MovieDetailsInfoFragment()
        }
    }
}