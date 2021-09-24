package com.johnsondev.doboshacademyapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.johnsondev.doboshacademyapp.views.actordetails.pagerfragments.ActorDetailsAboutFragment
import com.johnsondev.doboshacademyapp.views.actordetails.pagerfragments.ActorDetailsMoviesFragment
import com.johnsondev.doboshacademyapp.views.favorite.pagerfragments.FavoriteActorsFragment
import com.johnsondev.doboshacademyapp.views.favorite.pagerfragments.FavoriteMoviesFragment

class FavoritesPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteMoviesFragment()
            else -> FavoriteActorsFragment()
        }
    }
}