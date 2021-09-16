package com.johnsondev.doboshacademyapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.johnsondev.doboshacademyapp.utilities.Constants.RECOMMENDATIONS_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.SIMILAR_LIST_TYPE
import com.johnsondev.doboshacademyapp.views.moviedetails.pagerfragments.MovieDetailsActorsFragment
import com.johnsondev.doboshacademyapp.views.moviedetails.pagerfragments.MovieDetailsInfoFragment
import com.johnsondev.doboshacademyapp.views.moviedetails.pagerfragments.MovieDetailsRecommendFragment
import com.johnsondev.doboshacademyapp.views.moviedetails.pagerfragments.MovieDetailsTrailersFragment

class MovieDetailsPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> MovieDetailsInfoFragment()
            1 -> MovieDetailsActorsFragment()
            2 -> MovieDetailsRecommendFragment(RECOMMENDATIONS_LIST_TYPE)
            3 -> MovieDetailsRecommendFragment(SIMILAR_LIST_TYPE)
            else -> MovieDetailsTrailersFragment()
        }
    }
}