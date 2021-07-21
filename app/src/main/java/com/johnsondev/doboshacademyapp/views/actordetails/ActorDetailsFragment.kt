package com.johnsondev.doboshacademyapp.views.actordetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_PATH
import com.johnsondev.doboshacademyapp.utilities.animateView
import com.johnsondev.doboshacademyapp.viewmodel.MovieDetailsViewModel


class ActorDetailsFragment : Fragment() {

    private lateinit var detailsViewModel: MovieDetailsViewModel
    private lateinit var tvName: TextView
    private lateinit var tvBirthDay: TextView
    private lateinit var tvDeathDay: TextView
    private lateinit var tvPlaceOfBirth: TextView
    private lateinit var tvBiography: TextView
    private lateinit var ivBioMask: ImageView
    private lateinit var ivPosterProfile: ImageView
    private lateinit var backToMovieDetailsBtn: View

    private lateinit var birthDayView: TextView
    private lateinit var deathDayView: TextView
    private lateinit var placeOfBirthView: TextView

    private lateinit var fragmentBackgroundLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_actor_details, container, false)

        initViews(view)
        initListeners()

        return view
    }

    private fun initViews(view: View) {

        tvName = view.findViewById(R.id.tv_actor_name_detail)
        tvBirthDay = view.findViewById(R.id.tv_birth_day)
        tvPlaceOfBirth = view.findViewById(R.id.tv_place_of_birth)
        tvDeathDay = view.findViewById(R.id.tv_death_day)
        tvBiography = view.findViewById(R.id.tv_biography)
        ivBioMask = view.findViewById(R.id.bio_mask)
        ivPosterProfile = view.findViewById(R.id.iv_actor_profile_poster)
        backToMovieDetailsBtn = view.findViewById(R.id.back_to_detail_view_group)

        birthDayView = view.findViewById(R.id.birth_day)
        deathDayView = view.findViewById(R.id.death_day)
        placeOfBirthView = view.findViewById(R.id.place_of_birth)

        fragmentBackgroundLayout = view.findViewById(R.id.actor_details_background)

    }

    private fun initListeners() {

        tvBiography.setOnClickListener {
            when (tvBiography.maxLines) {
                15 -> {
                    animateView(tvBiography, "maxLines", 1000, 15f, 100f)
                        .start()
                }
                else -> {
                    animateView(tvBiography, "maxLines", 1000, 100f, 15f)
                        .start()
                }
            }
        }

        backToMovieDetailsBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        detailsViewModel.getActorDetails().observe(viewLifecycleOwner) {
            tvName.text = it.name
            tvBirthDay.text = it.birthDay
            tvPlaceOfBirth.text = it.placeOfBirth
            tvBiography.text = it.biography

            if (it.deathDay == null) deathDayView.isVisible = false
            if (it.birthDay == null) birthDayView.isVisible = false
            if (it.placeOfBirth == null) placeOfBirthView.isVisible = false

            val imagePath = "$POSTER_PATH${it.profilePath}"

            ivPosterProfile.clipToOutline = true
            ivPosterProfile.load(imagePath) {
                crossfade(true)
                placeholder(R.drawable.ic_baseline_person_24)
                fallback(R.drawable.ic_baseline_person_24)
                error(R.drawable.ic_baseline_person_24)
            }

        }

        detailsViewModel.getActorMovieCredits().observe(viewLifecycleOwner) { t ->
            Log.d("TAG", t.toString())
        }

        detailsViewModel.getActorImages().observe(viewLifecycleOwner) { t ->
            Log.d("TAG", t.toString())
        }

        detailsViewModel.getAverageColorBody().observe(viewLifecycleOwner) {
            fragmentBackgroundLayout.background = it.toDrawable()
            activity?.window?.statusBarColor = it
            animateView(fragmentBackgroundLayout, "alpha", 750,  1f).start()

        }

        detailsViewModel.getAverageColorText().observe(viewLifecycleOwner) {
            birthDayView.setTextColor(it)
            deathDayView.setTextColor(it)
            placeOfBirthView.setTextColor(it)
            tvBiography.setTextColor(it)
        }

        // TODO: 20.07.2021 handle timeout exception

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
    }


    override fun onDestroy() {
        super.onDestroy()
        detailsViewModel.clearActorDetails()
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.main_color)
    }


}