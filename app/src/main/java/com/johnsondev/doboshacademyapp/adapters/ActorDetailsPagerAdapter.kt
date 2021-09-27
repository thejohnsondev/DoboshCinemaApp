package com.johnsondev.doboshacademyapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.johnsondev.doboshacademyapp.ui.actordetails.pagerfragments.ActorDetailsAboutFragment
import com.johnsondev.doboshacademyapp.ui.actordetails.pagerfragments.ActorDetailsMoviesFragment

class ActorDetailsPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ActorDetailsAboutFragment()
            else -> ActorDetailsMoviesFragment()
        }
    }
}