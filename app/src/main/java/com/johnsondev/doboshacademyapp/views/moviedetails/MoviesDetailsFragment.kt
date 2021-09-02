package com.johnsondev.doboshacademyapp.views.moviedetails

import android.icu.text.CaseMap
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.BlurTransformation
import coil.transform.GrayscaleTransformation
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorsAdapter
import com.johnsondev.doboshacademyapp.adapters.OnActorItemClickListener
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.network.dto.MovieVideoDto
import com.johnsondev.doboshacademyapp.utilities.Constants.ACTOR_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.BACKDROP_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_MINI
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.TRAILERS_KEY
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.utilities.showMessage
import com.johnsondev.doboshacademyapp.utilities.timeToHFromMin
import com.johnsondev.doboshacademyapp.viewmodel.MovieDetailsViewModel
import kotlinx.coroutines.*
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

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

    private lateinit var ivBackdrop: ImageView
    private lateinit var ivPoster: ImageView
    private lateinit var tvTitle: TextView

    private lateinit var tvTagline: TextView
    private lateinit var tvTimeInH: TextView
    private lateinit var rbMovieRating: RatingBar
    private lateinit var tvReviews: TextView
    private lateinit var tvAge: TextView

    private lateinit var detailsViewModel: MovieDetailsViewModel
    private val scope = CoroutineScope(Dispatchers.IO + Job())


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
//        unavailableMoviePlaceholder = view.findViewById(R.id.unavailable_movie_details_placeholder)
//
//        adapter = ActorsAdapter(requireContext(), clickListener, ITEM_TYPE_MINI)
//        rvActors = view.findViewById(R.id.rv_actors)
//        rvActors?.adapter = adapter
//        rvActors?.setHasFixedSize(true)

        ivBackdrop = view.findViewById(R.id.iv_backdrop)
        ivPoster = view.findViewById(R.id.iv_poster)
        tvTitle = view.findViewById(R.id.tv_title)

        tvTagline = view.findViewById(R.id.tv_tagline)
        tvTimeInH = view.findViewById(R.id.tv_time_in_h)
        rbMovieRating = view.findViewById(R.id.rb_rating)
        tvReviews = view.findViewById(R.id.tv_reviews)
        tvAge = view.findViewById(R.id.tv_age)

    }

    override fun layoutId(): Int = R.layout.fragment_movies_details_redesign

    override fun loadData() {
//        date = Calendar.getInstance()

        val movieId = arguments?.getInt(MOVIE_KEY)

        if (movieId != 0 && movieId != null) {
            detailsViewModel.loadMovieFromNetById(movieId)
            detailsViewModel.loadCastForMovieById(movieId)
            detailsViewModel.loadMovieVideosById(movieId)
            detailsViewModel.loadMovieImagesById(movieId)
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

            tvTitle.text = movie.title
//            tvYear.text = "(${movie.releaseDate.substring(0, 3)})"
//            tvYear.text = getString(
//                R.string.movie_release_year_placeholder,
//                movie.releaseDate.substring(0, 4)
//            )
            tvTagline.text = movie.tagLine
//            tvTimeInH.text =
//                "${timeToHFromMin(movie.runtime!!)[0]} H ${timeToHFromMin(movie.runtime!!)[1]} MIN"
            tvTimeInH.text = getString(
                R.string.movie_time_in_h,
                timeToHFromMin(movie.runtime!!)[0],
                timeToHFromMin(movie.runtime)[1]
            )
            rbMovieRating.progress = (movie.ratings * 2).toInt()
            tvReviews.text = movieReviews
            tvAge.text = getString(R.string.plus, movie.minimumAge)


        })

        detailsViewModel.getActorsForCurrentMovie().observeOnce(this, {

        })

        detailsViewModel.getCrewForCurrentMovie().observeOnce(this, {

        })

        detailsViewModel.getMovieVideos().observeOnce(this, {

        })

        detailsViewModel.getMovieImagesForCurrentMovie().observeOnce(this, {

        })

        detailsViewModel.error.observeOnce(this, {
            if (it != null) {
                onError(it)
//                tvTitle?.isVisible = false
//                headImage?.isVisible = false
//                tvGenres?.isVisible = false
//                tvReviews?.isVisible = false
//                movieRating?.isVisible = false
//                tvDescription?.isVisible = false
//                tvStoryLine?.isVisible = false
//                tvCast?.isVisible = false
//                addToCalendarBtn?.isVisible = false
//                rvActors?.adapter = null
//                watchTheTrailerBtn?.isVisible = false
//                unavailableMoviePlaceholder.visibility = View.VISIBLE
            }
        })
    }


    private val clickListener = object : OnActorItemClickListener {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onClick(actor: Actor) {
            doOnClick(actor)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun doOnClick(actor: Actor) {

        val bundle = Bundle()
        bundle.putParcelable(ACTOR_KEY, actor)
        findNavController().navigate(
            R.id.action_moviesDetailsFragment_to_actorDetailsFragment,
            bundle
        )


    }

    override fun onDestroy() {
        super.onDestroy()

    }
}







