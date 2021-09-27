package com.johnsondev.doboshacademyapp.ui.actordetails

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.BlurTransformation
import com.google.android.material.tabs.TabLayoutMediator
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorDetailsPagerAdapter
import com.johnsondev.doboshacademyapp.databinding.FragmentActorDetailsBinding
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.ACTOR_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_PATH
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragmentBinding

class ActorDetailsFragment : BaseFragmentBinding(R.layout.fragment_actor_details) {

    private val detailsViewModel by viewModels<ActorDetailsViewModel>()
    private val binding by viewBinding(FragmentActorDetailsBinding::bind)
    private var currentActorId: Int? = null


    override fun initFields() {
        binding.actorViewPager.adapter = ActorDetailsPagerAdapter(this)
        binding.actorViewPager.offscreenPageLimit = 1

        TabLayoutMediator(binding.actorDetailsTabLayout, binding.actorViewPager) { tab, pos ->
            tab.text = Constants.ACTOR_TAB_TITLES[pos]
            binding.actorViewPager.setCurrentItem(tab.position, true)
        }.attach()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun loadData() {
        currentActorId = arguments?.getInt(ACTOR_KEY)

        if (detailsViewModel.checkInternetConnection(requireContext())) {
            if (currentActorId != 0) {
                detailsViewModel.apply {
                    loadActorDetailsById(currentActorId!!)
                    loadFavoriteActorsIds()
                }
            }
        }
    }

    override fun bindViews() {}

    override fun initListenersAndObservers() {

        with(binding) {
            detailsViewModel.getActorDetails().observe(viewLifecycleOwner) {

                if (detailsViewModel.isActorFavorite(currentActorId!!)) {
                    favoriteActorBtn.load(R.drawable.ic_favorite_filled)
                }

                val imagePath = "$POSTER_PATH${it.profilePath}"

                ivActorPoster.clipToOutline = true
                ivActorPoster.load(imagePath) {
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_person_24)
                    error(R.drawable.ic_baseline_person_24)
                }

                ivActorBackdrop.load(imagePath) {
                    crossfade(true)
                    transformations(BlurTransformation(requireContext(), 15f))
                }

                tvActorName.text = it.name
                tvActorDepartment.text = it.department

            }

            detailsViewModel.error.observe(viewLifecycleOwner) {
                if (it != null) {
                    onError(it)
                    hideViews()
                }
            }

            favoriteActorBtn.setOnClickListener {
                detailsViewModel.loadFavoriteActorsIds()
                if (detailsViewModel.isActorFavorite(currentActorId!!)) {
                    detailsViewModel.deleteActorFromFavorites(currentActorId!!)
                    favoriteActorBtn.load(R.drawable.ic_favorite_icon)
                } else {
                    detailsViewModel.insertActorToFavorites(currentActorId!!)
                    favoriteActorBtn.load(R.drawable.ic_favorite_filled)
                }
            }
        }
    }

    private fun hideViews() {
        with(binding) {
            favoriteActorBtn.isVisible = false
            actorDetailsTabLayout.isVisible = false
            actorViewPager.isVisible = false
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        detailsViewModel.clearActorDetails()
    }


}