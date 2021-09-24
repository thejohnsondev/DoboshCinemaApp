package com.johnsondev.doboshacademyapp.views.actordetails

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.request.CachePolicy
import coil.transform.BlurTransformation
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorDetailsPagerAdapter
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.ACTOR_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_PATH
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.viewmodel.ActorDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class ActorDetailsFragment : BaseFragment() {

    private lateinit var detailsViewModel: ActorDetailsViewModel
    private var currentActorId: Int? = null

    private var ivActorPoster: ImageView? = null
    private var ivActorBackdrop: ImageView? = null
    private var tvActorName: TextView? = null
    private var tvActorDepartment: TextView? = null
    private var viewPager: ViewPager2? = null
    private var tabLayout: TabLayout? = null
    private var favoriteBtn: ImageView? = null


    override fun initViews(view: View) {

        detailsViewModel = ViewModelProvider(this)[ActorDetailsViewModel::class.java]

        ivActorPoster = view.findViewById(R.id.iv_actor_poster)
        ivActorBackdrop = view.findViewById(R.id.iv_actor_backdrop)
        tvActorName = view.findViewById(R.id.tv_actor_name)
        tvActorDepartment = view.findViewById(R.id.tv_actor_department)
        viewPager = view.findViewById(R.id.actor_view_pager)
        tabLayout = view.findViewById(R.id.actor_details_tab_layout)
        favoriteBtn = view.findViewById(R.id.favorite_actor_btn)


        viewPager?.adapter = ActorDetailsPagerAdapter(this)
        viewPager?.offscreenPageLimit = 1

        TabLayoutMediator(tabLayout!!, viewPager!!) { tab, pos ->
            tab.text = Constants.ACTOR_TAB_TITLES[pos]
            viewPager?.setCurrentItem(tab.position, true)
        }.attach()

    }

    override fun layoutId(): Int = R.layout.fragment_actor_details_redesign

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

    override fun bindViews(view: View) {}

    override fun initListenersAndObservers(view: View) {

        detailsViewModel.getActorDetails().observe(viewLifecycleOwner) {

            if (detailsViewModel.isActorFavorite(currentActorId!!)) {
                favoriteBtn?.load(R.drawable.ic_favorite_filled)
            }


            val imagePath = "$POSTER_PATH${it.profilePath}"

            ivActorPoster?.clipToOutline = true
            ivActorPoster?.load(imagePath) {
                crossfade(true)
                placeholder(R.drawable.ic_baseline_person_24)
                error(R.drawable.ic_baseline_person_24)
            }

            ivActorBackdrop?.load(imagePath) {
                crossfade(true)
                transformations(BlurTransformation(requireContext(), 15f))
            }

            tvActorName?.text = it.name
            tvActorDepartment?.text = it.department

        }

        detailsViewModel.error.observe(viewLifecycleOwner) {
            if (it != null) {
                onError(it)
                hideViews()
            }
        }

        favoriteBtn?.setOnClickListener {
            detailsViewModel.loadFavoriteActorsIds()
            if (detailsViewModel.isActorFavorite(currentActorId!!)) {
                detailsViewModel.deleteActorFromFavorites(currentActorId!!)
                favoriteBtn?.load(R.drawable.ic_favorite_icon)
            } else {
                detailsViewModel.insertActorToFavorites(currentActorId!!)
                favoriteBtn?.load(R.drawable.ic_favorite_filled)
            }
        }



    }

    private fun hideViews() {
        favoriteBtn?.isVisible = false
        tabLayout?.isVisible = false
        viewPager?.isVisible = false
    }


    override fun onDestroy() {
        super.onDestroy()
        detailsViewModel.clearActorDetails()
    }

}