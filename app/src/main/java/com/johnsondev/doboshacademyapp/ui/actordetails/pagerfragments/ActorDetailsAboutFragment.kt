package com.johnsondev.doboshacademyapp.ui.actordetails.pagerfragments

import android.view.View
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorImagesAdapter
import com.johnsondev.doboshacademyapp.adapters.NamesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnImageClickListener
import com.johnsondev.doboshacademyapp.data.network.dto.ActorImageProfileDto
import com.johnsondev.doboshacademyapp.databinding.FragmentActorDetailsAboutBinding
import com.johnsondev.doboshacademyapp.ui.actordetails.ActorDetailsViewModel
import com.johnsondev.doboshacademyapp.ui.actordetails.ActorDetailsViewModelFactory
import com.johnsondev.doboshacademyapp.utilities.Constants.ANIM_PROPERTY_MAX_LINES
import com.johnsondev.doboshacademyapp.utilities.Constants.CURRENT_YEAR
import com.johnsondev.doboshacademyapp.utilities.Constants.EMPTY_STRING
import com.johnsondev.doboshacademyapp.utilities.animateView
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ActorDetailsAboutFragment : BaseFragment(R.layout.fragment_actor_details_about), KodeinAware {

    override val kodein by kodein()

    private val factory: ActorDetailsViewModelFactory by instance()
    private val viewModel: ActorDetailsViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[ActorDetailsViewModel::class.java]
    }
    private val binding by viewBinding(FragmentActorDetailsAboutBinding::bind)
    private var alsoKnownAdapter: NamesAdapter? = null
    private var actorImagesAdapter: ActorImagesAdapter? = null

    override fun initFields() {
        alsoKnownAdapter = NamesAdapter(requireContext())
        actorImagesAdapter = ActorImagesAdapter(requireContext(), imageClickListener)
    }

    override fun loadData() {}

    override fun bindViews() {
        binding.rvAlsoKnown.adapter = alsoKnownAdapter
        binding.rvActorImages.adapter = actorImagesAdapter
    }

    override fun initListenersAndObservers() {
        with(binding) {

            viewModel.getActorDetails().observeOnce(viewLifecycleOwner, {

                if (it.birthDay.isNotEmpty()) {
                    tvAge.text =
                        (CURRENT_YEAR.minus(it.birthDay.substring(0, 4).toInt()).toString())
                }

                tvDateOfBirth.text = it.birthDay
                when (it.deathDay) {
                    EMPTY_STRING -> {
                        tvDateOfDeath.visibility = View.GONE
                        dateOfDeathText.visibility = View.GONE
                    }
                    else -> {
                        tvDateOfDeath.visibility = View.VISIBLE
                        dateOfDeathText.visibility = View.VISIBLE
                        tvDateOfDeath.text = it.deathDay
                    }
                }
                tvPlaceOfBirth.text = it.placeOfBirth
                tvBiography.text = it.biography
                alsoKnownAdapter?.setNamesList(it.alsoKnownAs)


            })

            viewModel.getActorImages().observeOnce(viewLifecycleOwner, {
                tvImagesCount.text = it.size.toString()
                actorImagesAdapter?.setActorImages(it)
            })

            binding.tvBiography.setOnClickListener {
                when (tvBiography.maxLines) {
                    15 -> {
                        animateView(tvBiography, ANIM_PROPERTY_MAX_LINES, 1000, 15f, 100f)
                            .start()
                    }
                    else -> {
                        animateView(tvBiography, ANIM_PROPERTY_MAX_LINES, 1000, 100f, 15f)
                            .start()
                    }
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