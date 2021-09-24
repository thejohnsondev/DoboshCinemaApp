package com.johnsondev.doboshacademyapp.views.moviedetails

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.transform.BlurTransformation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.GenresAdapter
import com.johnsondev.doboshacademyapp.adapters.MovieDetailsPagerAdapter
import com.johnsondev.doboshacademyapp.adapters.OnGenreClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Genre
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_TAB_TITLES
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.utilities.timeToHFromMin
import com.johnsondev.doboshacademyapp.viewmodel.MovieDetailsViewModel
import java.util.*
import javax.net.ssl.CertPathTrustManagerParameters

class MoviesDetailsFragment : BaseFragment() {

    private var date: Calendar? = null

    private lateinit var ivBackBtn: ImageView
    private lateinit var ivBackdrop: ImageView
    private lateinit var ivPoster: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvTagline: TextView
    private lateinit var tvTimeInH: TextView
    private lateinit var rbMovieRating: RatingBar
    private lateinit var tvReviews: TextView
    private lateinit var tvAge: TextView
    private lateinit var moreBtn: ImageView
    private lateinit var favoriteBtn: ImageView
    private lateinit var rvMovieGenres: RecyclerView
    private lateinit var movieGenresAdapter: GenresAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var unavailableMoviePlaceholder: View
    private lateinit var detailsViewModel: MovieDetailsViewModel
    private var currentMovieId: Int = 0
    private var currentMovie: Movie? = null

    private lateinit var timeIcon: ImageView
    private lateinit var ageBox: View
    private lateinit var genresText: TextView

    private lateinit var bottomSheet: View
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var watchLaterBtn: View


    override fun initViews(view: View) {

        detailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]


        unavailableMoviePlaceholder = view.findViewById(R.id.unavailable_movie_details_placeholder)
        ivBackBtn = view.findViewById(R.id.back_btn)
        ivBackdrop = view.findViewById(R.id.iv_backdrop_rv_item)
        ivPoster = view.findViewById(R.id.iv_poster_rv_item)
        tvTitle = view.findViewById(R.id.tv_title)
        tvTagline = view.findViewById(R.id.tv_tagline)
        tvTimeInH = view.findViewById(R.id.tv_time_in_h)
        rbMovieRating = view.findViewById(R.id.rb_rating)
        tvReviews = view.findViewById(R.id.tv_reviews)
        tvAge = view.findViewById(R.id.tv_age)
        moreBtn = view.findViewById(R.id.more_btn)
        favoriteBtn = view.findViewById(R.id.favorite_btn)
        rvMovieGenres = view.findViewById(R.id.rv_movie_genres)
        movieGenresAdapter = GenresAdapter(view.context, onGenreClickListener)
        rvMovieGenres.adapter = movieGenresAdapter

        ageBox = view.findViewById(R.id.age_shape)
        timeIcon = view.findViewById(R.id.time_icon)
        genresText = view.findViewById(R.id.genres_text)
        watchLaterBtn = view.findViewById(R.id.add_to_calendar_btn)

        tabLayout = view.findViewById(R.id.movie_details_tab_layout)
        viewPager2 = view.findViewById(R.id.movie_view_pager)

        viewPager2.adapter = MovieDetailsPagerAdapter(this)
        viewPager2.offscreenPageLimit = 1


        TabLayoutMediator(tabLayout, viewPager2) { tab, pos ->
            tab.text = MOVIE_TAB_TITLES[pos]
            viewPager2.setCurrentItem(tab.position, true)

        }.attach()

        bottomSheet = view.findViewById(R.id.details_bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN


    }

    override fun layoutId(): Int = R.layout.fragment_movies_details_redesign

    override fun loadData() {
        date = Calendar.getInstance()

        val movieId = arguments?.getInt(MOVIE_KEY)

        if (movieId != 0 && movieId != null) {
            detailsViewModel.apply {
                loadMovieFromNetById(movieId)
                loadCastForMovieById(movieId)
                loadMovieVideosById(movieId)
                loadMovieImagesById(movieId)
                loadRecommendationsByMovieId(movieId)
                loadSimilarMoviesById(movieId)
                loadFavoriteMoviesIds()
            }

        }

    }

    override fun bindViews(view: View) {
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.main_color)

    }

    override fun initListenersAndObservers(view: View) {

        detailsViewModel.getCurrentMovieFromNet().observeOnce(this, { movie ->

            currentMovieId = movie.id
            currentMovie = Movie(id = movie.id, title = movie.title)

            if (detailsViewModel.isMovieFavorite(currentMovieId)) {
                favoriteBtn.load(R.drawable.ic_favorite_filled)
            }

            val movieReviews: String =
                view.context.getString(R.string.reviews, movie.numberOfRatings)

            ivBackdrop.alpha = 0.4f
            ivBackdrop.load(movie.backdrop) {
                crossfade(500)
                fallback(R.drawable.movie_placeholder)
                error(R.drawable.movie_placeholder)
                transformations(BlurTransformation(requireContext(), 2f))
            }

            ivPoster.load(movie.poster) {
                crossfade(true)
                placeholder(R.drawable.movie_placeholder)
                fallback(R.drawable.movie_placeholder)
                error(R.drawable.movie_placeholder)
            }

            ivPoster.clipToOutline = true

            tvTitle.text = getString(
                R.string.movie_title_placeholder,
                movie.title,
                movie.releaseDate.substring(6, 10)
            )
            tvTagline.text = movie.tagLine
            tvTimeInH.text = getString(
                R.string.movie_time_in_h,
                timeToHFromMin(movie.runtime)[0],
                timeToHFromMin(movie.runtime)[1]
            )
            rbMovieRating.progress = (movie.ratings * 2).toInt()
            tvReviews.text = movieReviews
            tvAge.text = getString(R.string.plus, movie.minimumAge)
            movieGenresAdapter.setGenresList(movie.genres ?: emptyList())

        })

        detailsViewModel.error.observe(viewLifecycleOwner) {
            if (it != null) {
                onError(it)
                hideViews()
            }
        }

        moreBtn.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        favoriteBtn.setOnClickListener {
            detailsViewModel.loadFavoriteMoviesIds()
            if (detailsViewModel.isMovieFavorite(currentMovieId)) {
                detailsViewModel.deleteMovieFromFavorites(currentMovieId)
                favoriteBtn.load(R.drawable.ic_favorite_icon)
            } else {
                detailsViewModel.insertMovieToFavorites(currentMovieId)
                favoriteBtn.load(R.drawable.ic_favorite_filled)
            }

        }

        watchLaterBtn.setOnClickListener {
            detailsViewModel.callDatePicker(requireContext(), date!!, currentMovie!!)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        ivBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun hideViews() {
        rbMovieRating.isVisible = false
        moreBtn.isVisible = false
        ageBox.isVisible = false
        timeIcon.isVisible = false
        genresText.isVisible = false
        viewPager2.isVisible = false
        tabLayout.isVisible = false
        unavailableMoviePlaceholder.visibility = View.VISIBLE
    }


    private val onGenreClickListener = object : OnGenreClickListener {
        override fun onClick(genre: Genre) {
            val bundleWithGenre = Bundle()
            bundleWithGenre.putParcelable(Constants.GENRE_KEY, genre)
            bundleWithGenre.putString(Constants.SPECIFIC_LIST_TYPE, Constants.GENRE_SPEC_TYPE)
            findNavController().navigate(
                R.id.action_moviesDetailsFragment_to_specificListFragment,
                bundleWithGenre
            )
        }
    }

}







