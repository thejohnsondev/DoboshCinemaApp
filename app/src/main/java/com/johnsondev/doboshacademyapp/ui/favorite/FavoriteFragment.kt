package com.johnsondev.doboshacademyapp.ui.favorite

import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.*
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.isInternetConnectionAvailable
import com.johnsondev.doboshacademyapp.utilities.observeOnce


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
                hideViews()
            }
        })

    }

    private fun hideViews(){
        tabLayout?.visibility = View.GONE
        viewPager?.visibility = View.GONE

    }

}