package com.johnsondev.doboshacademyapp.views.actordetails

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.utilities.animateView


class ActorDetailsFragment : Fragment() {


    private lateinit var tvBiography: TextView
    private lateinit var ivBioMask: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_actor_details, container, false)


        val deathDay = view.findViewById<TextView>(R.id.death_day)
        val tvDeathDay = view.findViewById<TextView>(R.id.tv_death_day)
//        deathDay.isVisible = false
//        tvDeathDay.isVisible = false


        tvBiography = view.findViewById(R.id.tv_biography)
        ivBioMask = view.findViewById(R.id.bio_mask)



        tvBiography.setOnClickListener {
            when (tvBiography.maxLines) {
                15 -> {
                    val maxLinesAnim = animateView(
                        tvBiography, "maxLines", 1000,
                        15f, 100f
                    )
                    val bioMaskAnim = animateView(
                        ivBioMask, "alpha", 1000,
                        1f, 0f
                    )

                    AnimatorSet().apply {
                        play(maxLinesAnim).with(bioMaskAnim)
                        start()
                    }
                }
                else -> {
                    val maxLinesAnim = animateView(
                        tvBiography, "maxLines", 1000,
                        100f, 15f
                    )
                    val bioMaskAnim = animateView(
                        ivBioMask, "alpha", 1000,
                        0f, 1f
                    )

                    AnimatorSet().apply {
                        play(maxLinesAnim).with(bioMaskAnim)
                        start()
                    }
                }
            }
        }
        return view
    }


}