package com.johnsondev.doboshacademyapp.views.actordetails.pagerfragments

import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorImagesAdapter
import com.johnsondev.doboshacademyapp.adapters.NamesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnGenreClickListener
import com.johnsondev.doboshacademyapp.adapters.OnImageClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Genre
import com.johnsondev.doboshacademyapp.data.network.dto.ActorImageProfileDto
import com.johnsondev.doboshacademyapp.utilities.Constants.CURRENT_YEAR
import com.johnsondev.doboshacademyapp.utilities.animateView
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.viewmodel.ActorDetailsViewModel


class ActorDetailsAboutFragment : BaseFragment() {

    private val viewModel by viewModels<ActorDetailsViewModel>()
    private var tvAge: TextView? = null
    private var tvDateOfBirth: TextView? = null
    private var tvDateOfDeath: TextView? = null
    private var textDateOfBirth: TextView? = null
    private var tvPlaceOfBirth: TextView? = null
    private var tvBiography: TextView? = null
    private var rvAlsoKnown: RecyclerView? = null
    private var alsoKnownAdapter: NamesAdapter? = null
    private var tvImagesCount: TextView? = null
    private var rvActorImages: RecyclerView? = null
    private var actorImagesAdapter: ActorImagesAdapter? = null

    override fun initViews(view: View) {

        tvAge = view.findViewById(R.id.tv_age)
        tvDateOfBirth = view.findViewById(R.id.tv_date_of_birth)
        tvDateOfDeath = view.findViewById(R.id.tv_date_of_death)
        textDateOfBirth = view.findViewById(R.id.date_of_death_text)
        tvPlaceOfBirth = view.findViewById(R.id.tv_place_of_birth)
        tvBiography = view.findViewById(R.id.tv_biography)
        rvAlsoKnown = view.findViewById(R.id.rv_also_known)
        alsoKnownAdapter = NamesAdapter(requireContext())
        tvImagesCount = view.findViewById(R.id.tv_images_count)
        rvActorImages = view.findViewById(R.id.rv_actor_images)
        actorImagesAdapter = ActorImagesAdapter(requireContext(), imageClickListener)

    }

    override fun layoutId(): Int = R.layout.fragment_actor_details_about

    override fun loadData() {}

    override fun bindViews(view: View) {
        rvAlsoKnown?.adapter = alsoKnownAdapter
        rvActorImages?.adapter = actorImagesAdapter
    }

    override fun initListenersAndObservers(view: View) {

        viewModel.getActorDetails().observeOnce(this, {
            if(it.birthDay.isNotEmpty()){
                tvAge?.text =
                    (CURRENT_YEAR.minus(it.birthDay.substring(0, 4).toInt()).toString())
            }
            tvDateOfBirth?.text = it.birthDay
            when (it.deathDay) {
                "" -> {
                    tvDateOfDeath?.visibility = View.GONE
                    textDateOfBirth?.visibility = View.GONE
                }
                else -> {
                    tvDateOfDeath?.visibility = View.VISIBLE
                    textDateOfBirth?.visibility = View.VISIBLE
                    tvDateOfDeath?.text = it.deathDay
                }
            }
            tvPlaceOfBirth?.text = it.placeOfBirth
            tvBiography?.text = it.biography
            alsoKnownAdapter?.setNamesList(it.alsoKnownAs)

        })

        viewModel.getActorImages().observeOnce(this, {
            tvImagesCount?.text = it.size.toString()
            actorImagesAdapter?.setActorImages(it)
        })

        tvBiography?.setOnClickListener {
            when (tvBiography?.maxLines) {
                15 -> {
                    animateView(tvBiography!!, "maxLines", 1000, 15f, 100f)
                        .start()
                }
                else -> {
                    animateView(tvBiography!!, "maxLines", 1000, 100f, 15f)
                        .start()
                }
            }
        }
    }


    private val imageClickListener = object : OnImageClickListener {
        override fun onClick(actorImage: ActorImageProfileDto) {
            // do nothing
        }

    }

}