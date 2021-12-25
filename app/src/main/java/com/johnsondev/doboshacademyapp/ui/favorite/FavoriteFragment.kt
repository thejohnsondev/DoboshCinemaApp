package com.johnsondev.doboshacademyapp.ui.favorite

import android.view.View
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.FavoritesPagerAdapter
import com.johnsondev.doboshacademyapp.databinding.FragmentFavoriteBinding
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.isInternetConnectionAvailable
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class FavoriteFragment : BaseFragment(R.layout.fragment_favorite), KodeinAware {

    override val kodein by kodein()

    private val factory: FavoritesViewModelFactory by instance()
    private val favoritesViewModel: FavoritesViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[FavoritesViewModel::class.java]
    }

    private val binding by viewBinding(FragmentFavoriteBinding::bind)

    override fun initFields() {
        binding.favoritesViewPager.adapter = FavoritesPagerAdapter(this)
        binding.favoritesViewPager.offscreenPageLimit = 1

        TabLayoutMediator(binding.favoritesTabLayout, binding.favoritesViewPager) { tab, pos ->
            tab.text = Constants.FAVORITES_TAB_TITLES[pos]
            binding.favoritesViewPager.setCurrentItem(tab.position, true)
        }.attach()
    }

    override fun loadData() {
        if (isInternetConnectionAvailable(activity?.application!!)) {
            favoritesViewModel.loadFavoriteMoviesFromDb()
            favoritesViewModel.loadFavoriteActorsFromDb()
        }
    }

    override fun bindViews() {}

    override fun initListenersAndObservers() {
        favoritesViewModel.error.observeOnce(viewLifecycleOwner, {
            if (it != null) {
                onError(it)
                hideViews()
            }
        })
    }

    private fun hideViews() {
        binding.favoritesTabLayout.visibility = View.GONE
        binding.favoritesViewPager.visibility = View.GONE
    }
}