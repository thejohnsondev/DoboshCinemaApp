package com.johnsondev.doboshacademyapp.ui.moviedetails

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.BlurTransformation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.GenresAdapter
import com.johnsondev.doboshacademyapp.adapters.MovieDetailsPagerAdapter
import com.johnsondev.doboshacademyapp.adapters.OnGenreClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Genre
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.databinding.FragmentMoviesDetailsBinding
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_TAB_TITLES
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.utilities.timeToHFromMin
import java.util.*

class MoviesDetailsFragment : BaseFragment(R.layout.fragment_movies_details) {

    private val detailsViewModel by viewModels<MovieDetailsViewModel>()
    private val binding by viewBinding(FragmentMoviesDetailsBinding::bind)
    private var currentMovieId: Int = 0
    private var currentMovie: Movie? = null
    private var date: Calendar? = null
    private lateinit var movieGenresAdapter: GenresAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun initFields() {
        binding.movieViewPager.adapter = MovieDetailsPagerAdapter(this)
        with(binding) {
            movieGenresAdapter = GenresAdapter(requireContext(), onGenreClickListener)
            rvMovieGenres.adapter = movieGenresAdapter
            movieViewPager.offscreenPageLimit = 1

            TabLayoutMediator(movieDetailsTabLayout, movieViewPager) { tab, pos ->
                tab.text = MOVIE_TAB_TITLES[pos]
                movieViewPager.setCurrentItem(tab.position, true)

            }.attach()

            bottomSheetBehavior = BottomSheetBehavior.from(detailsBottomSheet.root)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

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

    override fun bindViews() {
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.main_color)
    }

    override fun initListenersAndObservers() {
        with(binding) {
            detailsViewModel.getCurrentMovieFromNet().observeOnce(viewLifecycleOwner, { movie ->
                currentMovieId = movie.id
                currentMovie = Movie(id = movie.id, title = movie.title)

                if (detailsViewModel.isMovieFavorite(currentMovieId)) {
                    favoriteBtn.load(R.drawable.ic_favorite_filled)
                }
                val movieReviews: String =
                    requireContext().getString(R.string.reviews, movie.numberOfRatings)
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
                rbRating.progress = (movie.ratings * 2).toInt()
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

            detailsBottomSheet.addToCalendarBtn.setOnClickListener {
                detailsViewModel.callDatePicker(requireContext(), date!!, currentMovie!!)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }

            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun hideViews() {
        with(binding) {
            ivPoster.visibility = View.GONE
            ivBackdrop.visibility = View.GONE
            tvTitle.visibility = View.GONE
            rvMovieGenres.visibility = View.GONE
            tvTagline.visibility = View.GONE
            tvReviews.visibility = View.GONE
            tvAge.visibility = View.GONE
            tvTimeInH.visibility = View.GONE
            favoriteBtn.visibility = View.GONE
            rbRating.visibility = View.GONE
            moreBtn.visibility = View.GONE
            ageShape.visibility = View.GONE
            timeIcon.visibility = View.GONE
            genresText.visibility = View.GONE
            movieViewPager.visibility = View.GONE
            movieDetailsTabLayout.visibility = View.GONE
            unavailableMovieDetailsPlaceholder.root.visibility = View.VISIBLE
        }
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







