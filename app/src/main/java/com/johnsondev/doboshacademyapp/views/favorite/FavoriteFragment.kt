package com.johnsondev.doboshacademyapp.views.favorite

import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.*
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_MINI
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ITEM_MINI
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.isInternetConnectionAvailable
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.utilities.states.Loading
import com.johnsondev.doboshacademyapp.viewmodel.FavoritesViewModel


class FavoriteFragment : BaseFragment() {

    private val favoritesViewModel by viewModels<FavoritesViewModel>()
    private var viewPager: ViewPager2? = null
    private var tabLayout: TabLayout? = null


    override fun initViews(view: View) {
        viewPager = view.findViewById(R.id.favorites_view_pager)
        tabLayout = view.findViewById(R.id.favorites_tab_layout)

        viewPager?.adapter = FavoritesPagerAdapter(this)
        viewPager?.offscreenPageLimit = 1

        TabLayoutMediator(tabLayout!!, viewPager!!) { tab, pos ->
            tab.text = Constants.FAVORITES_TAB_TITLES[pos]
            viewPager?.setCurrentItem(tab.position, true)
        }.attach()
    }

    override fun layoutId(): Int = R.layout.fragment_favorite

    override fun loadData() {
        if (isInternetConnectionAvailable(activity?.application!!)) {
            favoritesViewModel.loadFavoriteMoviesFromDb()
            favoritesViewModel.loadFavoriteActorsFromDb()
        }
    }

    override fun bindViews(view: View) {

    }

    override fun initListenersAndObservers(view: View) {

        favoritesViewModel.error.observeOnce(viewLifecycleOwner, {
            if(it != null){
                onError(it)
            }
        })


//        favoritesViewModel.moviesLoadingState.observeOnce(this, {
//            when (it) {
//                is Loading -> {
//                    favMoviesLoadingIndicator?.visibility = View.VISIBLE
//                    rvFavoriteMovies?.visibility = View.GONE
//                }
//            }
//        })
//
//        favoritesViewModel.actorsLoadingState.observeOnce(this, {
//            when (it) {
//                is Loading -> {
//                    favActorsLoadingIndicator?.visibility = View.VISIBLE
//                    rvFavoriteActors?.visibility = View.GONE
//                }
//            }
//        })
//
//        favoritesViewModel.error.observe(viewLifecycleOwner) {
//            if (it != null) {
//                onError(it)
//                favActorsLoadingIndicator?.isVisible = false
//                favMoviesLoadingIndicator?.isVisible = false
//                favMoviesSpecBtn?.isVisible = false
//                favActorsSpecBtn?.isVisible = false
//            }
//        }


    }


    private val onMovieClickListener = object : OnMovieItemClickListener {
        override fun onClick(movie: Movie) {
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailsActivity(movie.id)
            )
        }
    }

    private val onActorClickListener = object : OnActorItemClickListener {
        override fun onClick(actor: Actor) {
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToActorDetailsActivity(actor.id)
            )
        }

    }


}