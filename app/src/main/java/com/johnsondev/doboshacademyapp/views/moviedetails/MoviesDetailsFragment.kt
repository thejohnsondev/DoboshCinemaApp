package com.johnsondev.doboshacademyapp.views.moviedetails

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorsAdapter
import com.johnsondev.doboshacademyapp.adapters.OnActorItemClickListener
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.network.dto.MovieVideoDto
import com.johnsondev.doboshacademyapp.utilities.Constants.ACTOR_DETAILS_ID
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ID
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.TRAILERS_KEY
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.viewmodel.MovieDetailsViewModel
import com.johnsondev.doboshacademyapp.views.actordetails.ActorDetailsFragment
import com.johnsondev.doboshacademyapp.views.movietrailers.MovieTrailersFragment
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class MoviesDetailsFragment : BaseFragment() {

    private var currentMovie: Movie? = null
    private var movieVideos: ArrayList<MovieVideoDto>? = null
    private var tvTitle: TextView? = null
    private var tvAge: TextView? = null
    private var tvGenres: TextView? = null
    private var tvReviews: TextView? = null
    private var movieRating: RatingBar? = null
    private var tvDescription: TextView? = null
    private var tvStoryLine: TextView? = null
    private var tvCast: TextView? = null
    private var headImage: ImageView? = null
    private var rvActors: RecyclerView? = null
    private var adapter: ActorsAdapter? = null
    private var addToCalendarBtn: Button? = null
    private var backBtn: TextView? = null
    private var watchTheTrailerBtn: Button? = null
    private var date: Calendar? = null
    private lateinit var detailsViewModel: MovieDetailsViewModel
    private val scope = CoroutineScope(Dispatchers.IO + Job())


    override fun initViews(view: View) {

        detailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

        tvTitle = view.findViewById(R.id.tv_title)
        tvAge = view.findViewById(R.id.tv_age)
        tvGenres = view.findViewById(R.id.movie_genres)
        tvReviews = view.findViewById(R.id.tv_reviews)
        movieRating = view.findViewById(R.id.movie_rating_bar)
        tvDescription = view.findViewById(R.id.tv_biography)
        tvStoryLine = view.findViewById(R.id.tv_story_line)
        tvCast = view.findViewById(R.id.tv_cast)
        headImage = view.findViewById(R.id.head_image)
        addToCalendarBtn = view.findViewById(R.id.add_to_calendar_btn)
        backBtn = view.findViewById(R.id.back_btn)
        watchTheTrailerBtn = view.findViewById(R.id.watch_the_trailer_btn)

        adapter = ActorsAdapter(requireContext(), clickListener)
        rvActors = view.findViewById(R.id.rv_actors)
        rvActors?.adapter = adapter
        rvActors?.setHasFixedSize(true)

    }

    override fun layoutId(): Int = R.layout.fragment_movies_details

    override fun loadData() {
        date = Calendar.getInstance()

        val movieId = arguments?.getInt(MOVIE_ID)

        currentMovie = if (movieId != 0) {
            detailsViewModel.getMovieByIdFromDb(movieId!!)
        } else {
            arguments?.getParcelable(MOVIE_KEY)
        }

        if (detailsViewModel.checkInternetConnection(requireContext())) {
            scope.launch {
                if (currentMovie?.id!! != 0) {
                    detailsViewModel.loadActorsForMovieById(currentMovie?.id!!)
                    detailsViewModel.loadMovieVideosById(currentMovie?.id!!)
                }
            }
        } else {
            Toast.makeText(context, getString(R.string.unable_load_cast), Toast.LENGTH_LONG)
                .show()
            rvActors?.isVisible = false

        }

    }

    override fun bindViews(view: View) {
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.main_color)

        if (currentMovie?.id!! == 0) {
            tvReviews?.isVisible = false
            movieRating?.isVisible = false
            tvDescription?.isVisible = false
            tvStoryLine?.isVisible = false
            tvCast?.isVisible = false
            addToCalendarBtn?.isVisible = false
        }

        currentMovie?.let { movie ->
            val movieReviews: String =
                view.context.getString(R.string.reviews, movie.numberOfRatings)
            val movieAge: String = view.context.getString(R.string.plus, movie.minimumAge)
            tvTitle?.text = movie.title
            tvAge?.text = movieAge
            tvGenres?.text = movie.genres?.joinToString { it.name }
            tvReviews?.text = movieReviews
            movieRating?.progress = (movie.ratings * 2).toInt()
            tvDescription?.text = movie.overview

            detailsViewModel.getActorsForCurrentMovie()

            detailsViewModel.actorsList.observe(viewLifecycleOwner) {
                adapter?.setActors(it)
            }

            if (detailsViewModel.checkInternetConnection(requireContext())) {
                detailsViewModel.getMovieVideos().observe(viewLifecycleOwner) {
                    movieVideos = it as ArrayList<MovieVideoDto>?
                }
            }

            headImage?.load(movie.poster) {
                crossfade(true)
                placeholder(R.drawable.movie_placeholder)
                fallback(R.drawable.movie_placeholder)
                error(R.drawable.movie_placeholder)
            }
        }

    }

    override fun initListenersAndObservers(view: View) {

        addToCalendarBtn?.setOnClickListener {
            detailsViewModel.callDatePicker(requireContext(), date!!, currentMovie!!)
        }

        watchTheTrailerBtn?.setOnClickListener {
            if (detailsViewModel.checkInternetConnection(requireContext())) {
                val bundle = Bundle()
                bundle.putParcelableArrayList(TRAILERS_KEY, movieVideos)
                val trailersFragment = MovieTrailersFragment()
                trailersFragment.arguments = bundle

                parentFragmentManager.beginTransaction().apply {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    addToBackStack(null)
                    replace(R.id.main_container, trailersFragment)
                    commit()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.internet_connection_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        backBtn?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
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
        bundle.putParcelable(ACTOR_DETAILS_ID, actor)

        val fragment = ActorDetailsFragment()
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            replace(R.id.main_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

}

