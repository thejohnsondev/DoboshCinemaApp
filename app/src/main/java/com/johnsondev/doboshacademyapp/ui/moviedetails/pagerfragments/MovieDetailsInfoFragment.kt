package com.johnsondev.doboshacademyapp.ui.moviedetails.pagerfragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.clear
import coil.load
import coil.transform.RoundedCornersTransformation
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.CrewAdapter
import com.johnsondev.doboshacademyapp.adapters.ProductCompaniesAdapter
import com.johnsondev.doboshacademyapp.adapters.ProductCountriesAdapter
import com.johnsondev.doboshacademyapp.databinding.FragmentMovieDetailsInfoBinding
import com.johnsondev.doboshacademyapp.ui.moviedetails.MovieDetailsViewModel
import com.johnsondev.doboshacademyapp.utilities.Constants.BACKDROP_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.IMAGES_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_BACKDROP
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_POSTER
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_PATH
import com.johnsondev.doboshacademyapp.utilities.DtoMapper
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce

class MovieDetailsInfoFragment : BaseFragment(R.layout.fragment_movie_details_info) {

    private val detailsViewModel by viewModels<MovieDetailsViewModel>()
    private val binding by viewBinding(FragmentMovieDetailsInfoBinding::bind)
    private lateinit var filmCrewAdapter: CrewAdapter
    private lateinit var productCountriesAdapter: ProductCountriesAdapter
    private lateinit var productCompaniesAdapter: ProductCompaniesAdapter

    override fun initFields() {
        filmCrewAdapter = CrewAdapter(requireContext())
        productCountriesAdapter = ProductCountriesAdapter(requireContext())
        productCompaniesAdapter = ProductCompaniesAdapter(requireContext())
    }

    override fun loadData() {}

    override fun bindViews() {
        with(binding) {
            rvFilmCrew.adapter = filmCrewAdapter
            rvProductionCountries.adapter = productCountriesAdapter
            rvProductionCompanies.adapter = productCompaniesAdapter
        }
    }

    override fun initListenersAndObservers() {
        with(binding) {

            detailsViewModel.getCurrentMovieFromNet().observeOnce(viewLifecycleOwner, { movie ->
                movieInfoLoadingIndicator.visibility = View.GONE
                tvStoryLine.text = movie.overview
                tvStatus.text = movie.status
                tvReleaseDate.text = movie.releaseDate
                tvOrigTitle.text = movie.origTitle
                tvOrigLang.text = movie.origLanguage
                movie.productionCountries.map { DtoMapper.convertCountryFromDto(it) }
                    .let { productCountriesAdapter.setCountries(it) }
                tvRuntime.text = getString(R.string.running_time, movie.runtime)
                tvBudget.text = movie.budget.toString()
                tvRevenue.text = movie.revenue.toString()
                movie.productionCompanies.let { productCompaniesAdapter.setCompanies(it) }

            })

            detailsViewModel.getCrewForCurrentMovie().observeOnce(viewLifecycleOwner, {
                filmCrewAdapter.setCrewList(it)
            })

            detailsViewModel.getMovieImagesForCurrentMovie().observeOnce(viewLifecycleOwner, {

                if (it[POSTER_KEY]?.size != 0) {
                    ivMediaPoster.load("$POSTER_PATH${it[POSTER_KEY]?.get(0)?.filePath}") {
                        crossfade(true)
                        error(R.drawable.ic_baseline_image_24)
                        placeholder(R.drawable.ic_baseline_image_24)
                        transformations(RoundedCornersTransformation(10f))
                    }
                }

                if (it[BACKDROP_KEY]?.size != 0) {
                    ivMediaBackdrop.load("$POSTER_PATH${it[BACKDROP_KEY]?.get(0)?.filePath}") {
                        crossfade(true)
                        error(R.drawable.ic_baseline_image_24)
                        placeholder(R.drawable.ic_baseline_image_24)
                        transformations(RoundedCornersTransformation(10f))
                    }
                }

                tvPostersCount.text = getString(R.string.posters_count, it[POSTER_KEY]?.size)
                tvBackdropsCount.text = getString(R.string.backdrops_count, it[BACKDROP_KEY]?.size)

            })

            ivMediaPoster.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt(IMAGES_LIST_TYPE, ITEM_TYPE_POSTER)
                findNavController().navigate(
                    R.id.action_moviesDetailsFragment_to_specificImagesFragment,
                    bundle
                )
            }

            ivMediaBackdrop.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt(IMAGES_LIST_TYPE, ITEM_TYPE_BACKDROP)
                findNavController().navigate(
                    R.id.action_moviesDetailsFragment_to_specificImagesFragment,
                    bundle
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.ivMediaPoster.clear()
        binding.ivMediaBackdrop.clear()
    }
}