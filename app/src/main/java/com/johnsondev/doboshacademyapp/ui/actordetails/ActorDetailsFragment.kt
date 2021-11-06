package com.johnsondev.doboshacademyapp.ui.actordetails

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.BlurTransformation
import com.google.android.material.tabs.TabLayoutMediator
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorDetailsPagerAdapter
import com.johnsondev.doboshacademyapp.databinding.FragmentActorDetailsBinding
import com.johnsondev.doboshacademyapp.ui.favorite.FavoritesViewModel
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.ACTOR_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_PATH
import com.johnsondev.doboshacademyapp.utilities.appComponent
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import javax.inject.Inject

class ActorDetailsFragment : BaseFragment(R.layout.fragment_actor_details) {

    @Inject
    lateinit var factory: ActorDetailsViewModel.Factory
    private val viewModel: ActorDetailsViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[ActorDetailsViewModel::class.java]
    }

    private val binding by viewBinding(FragmentActorDetailsBinding::bind)
    private var currentActorId: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent().inject(this)
    }


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

        if (viewModel.checkInternetConnection(requireContext())) {
            if (currentActorId != 0) {
                viewModel.apply {
                    loadActorDetailsById(currentActorId!!)
                    loadFavoriteActorsIds()
                }
            }
        }
    }

    override fun bindViews() {}

    override fun initListenersAndObservers() {

        with(binding) {
            viewModel.getActorDetails().observe(viewLifecycleOwner) {

                if (viewModel.isActorFavorite(currentActorId!!)) {
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

            viewModel.error.observe(viewLifecycleOwner) {
                if (it != null) {
                    onError(it)
                    hideViews()
                }
            }

            favoriteActorBtn.setOnClickListener {
                viewModel.loadFavoriteActorsIds()
                if (viewModel.isActorFavorite(currentActorId!!)) {
                    viewModel.deleteActorFromFavorites(currentActorId!!)
                    favoriteActorBtn.load(R.drawable.ic_favorite_icon)
                } else {
                    viewModel.insertActorToFavorites(currentActorId!!)
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
        viewModel.clearActorDetails()
    }


}