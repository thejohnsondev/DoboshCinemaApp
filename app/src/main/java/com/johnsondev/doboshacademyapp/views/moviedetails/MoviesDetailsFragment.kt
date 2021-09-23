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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.GenresAdapter
import com.johnsondev.doboshacademyapp.adapters.MovieDetailsPagerAdapter
import com.johnsondev.doboshacademyapp.adapters.OnGenreClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Genre
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_TAB_TITLES
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.utilities.timeToHFromMin
import com.johnsondev.doboshacademyapp.viewmodel.MovieDetailsViewModel

class MoviesDetailsFragment : BaseFragment() {

//    private var currentMovie: Movie? = null
//    private var movieVideos: ArrayList<MovieVideoDto>? = null
//    private var tvTitle: TextView? = null
//    private var tvAge: TextView? = null
//    private var tvGenres: TextView? = null
//    private var tvReviews: TextView? = null
//    private var movieRating: RatingBar? = null
//    private var tvDescription: TextView? = null
//    private var tvStoryLine: TextView? = null
//    private var tvCast: TextView? = null
//    private var headImage: ImageView? = null
//    private var rvActors: RecyclerView? = null
//    private var adapter: ActorsAdapter? = null
//    private var addToCalendarBtn: Button? = null
//    private var backBtn: TextView? = null
//    private var watchTheTrailerBtn: Button? = null
//    private lateinit var unavailableMoviePlaceholder: View
//    private var date: Calendar? = null

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
    private lateinit var rvMovieGenres: RecyclerView
    private lateinit var movieGenresAdapter: GenresAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var unavailableMoviePlaceholder: View
    private lateinit var detailsViewModel: MovieDetailsViewModel
    private var currentMovieId: Int = 0

    private lateinit var timeIcon: ImageView
    private lateinit var ageBox: View
    private lateinit var genresText: TextView


    override fun initViews(view: View) {

        detailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

//        tvTitle = view.findViewById(R.id.tv_title)
//        tvAge = view.findViewById(R.id.tv_age)
//        tvGenres = view.findViewById(R.id.movie_genres)
//        tvReviews = view.findViewById(R.id.tv_reviews)
//        movieRating = view.findViewById(R.id.movie_rating_bar)
//        tvDescription = view.findViewById(R.id.tv_biography)
//        tvStoryLine = view.findViewById(R.id.tv_story_line)
//        tvCast = view.findViewById(R.id.tv_cast)
//        headImage = view.findViewById(R.id.head_image)
//        addToCalendarBtn = view.findViewById(R.id.add_to_calendar_btn)
//        backBtn = view.findViewById(R.id.back_btn)
//        watchTheTrailerBtn = view.findViewById(R.id.watch_the_trailer_btn)

//
//        adapter = ActorsAdapter(requireContext(), clickListener, ITEM_TYPE_MINI)
//        rvActors = view.findViewById(R.id.rv_actors)
//        rvActors?.adapter = adapter
//        rvActors?.setHasFixedSize(true)

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
        rvMovieGenres = view.findViewById(R.id.rv_movie_genres)
        movieGenresAdapter = GenresAdapter(view.context, onGenreClickListener)
        rvMovieGenres.adapter = movieGenresAdapter

        ageBox = view.findViewById(R.id.age_shape)
        timeIcon = view.findViewById(R.id.time_icon)
        genresText = view.findViewById(R.id.genres_text)

        tabLayout = view.findViewById(R.id.movie_details_tab_layout)
        viewPager2 = view.findViewById(R.id.movie_view_pager)

        viewPager2.adapter = MovieDetailsPagerAdapter(this)
        viewPager2.offscreenPageLimit = 1


        TabLayoutMediator(tabLayout, viewPager2) { tab, pos ->
            tab.text = MOVIE_TAB_TITLES[pos]
            viewPager2.setCurrentItem(tab.position, true)

        }.attach()


    }

    override fun layoutId(): Int = R.layout.fragment_movies_details_redesign

    override fun loadData() {
//        date = Calendar.getInstance()

        val movieId = arguments?.getInt(MOVIE_KEY)

        if (movieId != 0 && movieId != null) {
            detailsViewModel.apply {
                loadMovieFromNetById(movieId)
                loadCastForMovieById(movieId)
                loadMovieVideosById(movieId)
                loadMovieImagesById(movieId)
                loadRecommendationsByMovieId(movieId)
                loadSimilarMoviesById(movieId)
            }

        }

    }

    override fun bindViews(view: View) {
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.main_color)

    }

    override fun initListenersAndObservers(view: View) {

//        addToCalendarBtn?.setOnClickListener {
//            detailsViewModel.callDatePicker(requireContext(), date!!, currentMovie!!)
//        }
//
//        watchTheTrailerBtn?.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putParcelableArrayList(TRAILERS_KEY, movieVideos)
//            findNavController().navigate(
//                R.id.action_moviesDetailsFragment_to_movieTrailersFragment,
//                bundle
//            )
//        }
//
//        backBtn?.setOnClickListener {
//            findNavController().popBackStack()
//        }


        detailsViewModel.getCurrentMovieFromNet().observeOnce(this, { movie ->

//                view.context.getString(R.string.reviews, movie.numberOfRatings)
//            val movieAge: String = view.context.getString(R.string.plus, movie.minimumAge)
//            tvTitle?.text = movie.title
//            tvAge?.text = movieAge
//            tvGenres?.text = movie.genres?.joinToString { it.name }
//            tvReviews?.text = movieReviews
//            movieRating?.progress = (movie.ratings * 2).toInt()
//            tvDescription?.text = movie.overview
//
//
//            headImage?.load(movie.poster) {
//                crossfade(true)
//                placeholder(R.drawable.movie_placeholder)
//                fallback(R.drawable.movie_placeholder)
//                error(R.drawable.movie_placeholder)
//            }

            currentMovieId = movie.id

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
//            tvYear.text = getString(
//                R.string.movie_release_year_placeholder,
//                movie.releaseDate.substring(0, 4)
//            )
            tvTagline.text = movie.tagLine
//            tvTimeInH.text =
//                "${timeToHFromMin(movie.runtime!!)[0]} H ${timeToHFromMin(movie.runtime!!)[1]} MIN"
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
            detailsViewModel.insertMovieToFavorites(currentMovieId)
        }

        ivBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detailsViewModel.clearMovieDetails()
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







