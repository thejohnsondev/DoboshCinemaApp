package com.johnsondev.doboshacademyapp.views.moviedetails.pagerfragments

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.CrewAdapter
import com.johnsondev.doboshacademyapp.adapters.ProductCompaniesAdapter
import com.johnsondev.doboshacademyapp.adapters.ProductCountriesAdapter
import com.johnsondev.doboshacademyapp.utilities.Constants.BACKDROP_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_PATH
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.viewmodel.MovieDetailsViewModel

class MovieDetailsInfoFragment : BaseFragment() {

    private lateinit var tvStoryLine: TextView
    private lateinit var rvFilmCrew: RecyclerView
    private lateinit var tvStatus: TextView
    private lateinit var tvReleaseDate: TextView
    private lateinit var tvOrigTitle: TextView
    private lateinit var tvOrigLang: TextView
    private lateinit var rvProductCountries: RecyclerView
    private lateinit var tvRuntime: TextView
    private lateinit var tvBudget: TextView
    private lateinit var tvRevenue: TextView
    private lateinit var rvProductCompanies: RecyclerView
    private lateinit var ivMediaPoster: ImageView
    private lateinit var ivMediaBackdrop: ImageView
    private lateinit var tvPostersCount: TextView
    private lateinit var tvBackdrops: TextView

    private lateinit var filmCrewAdapter: CrewAdapter
    private lateinit var productCountriesAdapter: ProductCountriesAdapter
    private lateinit var productCompaniesAdapter: ProductCompaniesAdapter

    private lateinit var detailsViewModel: MovieDetailsViewModel


    override fun initViews(view: View) {
        detailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

        tvStoryLine = view.findViewById(R.id.tv_story_line)
        rvFilmCrew = view.findViewById(R.id.rv_film_crew)

        tvStatus = view.findViewById(R.id.tv_status)
        tvReleaseDate = view.findViewById(R.id.tv_release_date)
        tvOrigTitle = view.findViewById(R.id.tv_orig_title)
        tvOrigLang = view.findViewById(R.id.tv_orig_lang)
        rvProductCountries = view.findViewById(R.id.rv_production_countries)
        tvRuntime = view.findViewById(R.id.tv_runtime)
        tvBudget = view.findViewById(R.id.tv_budget)
        tvRevenue = view.findViewById(R.id.tv_revenue)
        rvProductCompanies = view.findViewById(R.id.rv_production_companies)
        ivMediaPoster = view.findViewById(R.id.iv_media_poster)
        ivMediaBackdrop = view.findViewById(R.id.iv_media_backdrop)
        tvPostersCount = view.findViewById(R.id.tv_posters_count)
        tvBackdrops = view.findViewById(R.id.tv_backdrops_count)

        filmCrewAdapter = CrewAdapter(requireContext())
        productCountriesAdapter = ProductCountriesAdapter(requireContext())
        productCompaniesAdapter = ProductCompaniesAdapter(requireContext())
    }

    override fun layoutId(): Int = R.layout.fragment_movie_details_info

    override fun loadData() {

    }

    override fun bindViews(view: View) {
        rvFilmCrew.adapter = filmCrewAdapter
        rvProductCountries.adapter = productCountriesAdapter
        rvProductCompanies.adapter = productCompaniesAdapter

    }

    override fun initListenersAndObservers(view: View) {

        detailsViewModel.getCurrentMovieFromNet().observeOnce(this, { movie ->
            tvStoryLine.text = movie.overview
            tvStatus.text = movie.status
            tvReleaseDate.text = movie.releaseDate
            tvOrigTitle.text = movie.origTitle
            tvOrigLang.text = movie.origLanguage
            movie.productionCountries?.let { productCountriesAdapter.setCountries(it) }
            tvRuntime.text = movie.runtime.toString()
            tvBudget.text = movie.budget.toString()
            tvRevenue.text = movie.revenue.toString()
            movie.productionCompanies?.let { productCompaniesAdapter.setCompanies(it) }

        })

        detailsViewModel.getCrewForCurrentMovie().observeOnce(this, {
            filmCrewAdapter.setCrewList(it)
        })

        detailsViewModel.getMovieImagesForCurrentMovie().observeOnce(this, {

            ivMediaPoster.load("$POSTER_PATH${it[POSTER_KEY]?.get(0)?.filePath}"){
                crossfade(true)
                error(R.drawable.ic_baseline_image_24)
                placeholder(R.drawable.ic_baseline_image_24)
                transformations(RoundedCornersTransformation(10f))
            }

            ivMediaBackdrop.load("$POSTER_PATH${it[BACKDROP_KEY]?.get(0)?.filePath}"){
                crossfade(true)
                error(R.drawable.ic_baseline_image_24)
                placeholder(R.drawable.ic_baseline_image_24)
                transformations(RoundedCornersTransformation(10f))
            }

            tvPostersCount.text = getString(R.string.posters_count,it[POSTER_KEY]?.size)
            tvBackdrops.text = getString(R.string.backdrops_count,it[BACKDROP_KEY]?.size)

        })

    }


}