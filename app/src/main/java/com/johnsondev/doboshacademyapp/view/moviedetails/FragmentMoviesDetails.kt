package com.johnsondev.doboshacademyapp.view.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.model.ActorsRepository
import com.johnsondev.doboshacademyapp.model.data.Movie
import com.johnsondev.doboshacademyapp.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.*

class FragmentMoviesDetails : Fragment() {

    private var currentMovie: Movie? = null

    private var tvTitle: TextView? = null
    private var tvAge: TextView? = null
    private var tvGenres: TextView? = null
    private var tvReviews: TextView? = null
    private var movieRating: RatingBar? = null
    private var tvStoryLine: TextView? = null
    private var headImage: ImageView? = null
    private var rvActors: RecyclerView? = null

    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private lateinit var actorsViewModel: ActorsViewModel

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)

        movieDetailViewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        actorsViewModel = ViewModelProvider(this)[ActorsViewModel::class.java]

        tvTitle = view.findViewById(R.id.tv_title)
        tvAge = view.findViewById(R.id.tv_age)
        tvGenres = view.findViewById(R.id.movie_genres)
        tvReviews = view.findViewById(R.id.tv_reviews)
        movieRating = view.findViewById(R.id.movie_rating_bar)
        tvStoryLine = view.findViewById(R.id.tv_description)
        headImage = view.findViewById(R.id.head_image)

        currentMovie?.let { movie ->
            val movieReviews: String =
                view.context.getString(R.string.reviews, movie.numberOfRatings)
            val movieAge: String = view.context.getString(R.string.plus, movie.minimumAge)
            tvTitle?.text = movie.title
            tvAge?.text = movieAge
            tvGenres?.text = movie.genres?.joinToString { it.name }
            tvReviews?.text = movieReviews
            movieRating?.progress = (movie.ratings * 2).toInt()
            tvStoryLine?.text = movie.overview
            val adapter = ActorsAdapter(context!!)
            rvActors = view.findViewById(R.id.rv_actors)
            rvActors?.adapter = adapter
            rvActors?.setHasFixedSize(true)

            actorsViewModel.getActorsForCurrentMovie().observe(this) {
                adapter.setActors(it)
            }

            headImage?.load(movie.poster) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                fallback(R.drawable.ic_launcher_foreground)
                error(R.drawable.target_img)
            }

        }

        val backBtn: TextView = view.findViewById(R.id.back_btn)
        backBtn.setOnClickListener() {
            fragmentManager?.popBackStack()
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentMovie = arguments?.getParcelable(MOVIE_KEY)
        scope.launch {
            ActorsRepository.loadActors(currentMovie?.id!!)
        }
    }

    companion object {
        const val MOVIE_KEY = "movie key"
    }

}