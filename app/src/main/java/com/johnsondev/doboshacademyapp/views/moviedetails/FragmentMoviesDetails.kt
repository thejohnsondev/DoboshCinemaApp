package com.johnsondev.doboshacademyapp.views.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorsAdapter
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.repositories.ActorsRepository
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_KEY
import com.johnsondev.doboshacademyapp.utilities.InternetConnectionManager
import com.johnsondev.doboshacademyapp.viewmodel.ActorsViewModel
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
    private var adapter: ActorsAdapter? = null

    private lateinit var actorsViewModel: ActorsViewModel

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    private lateinit var checkInternetConnection: InternetConnectionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)

        actorsViewModel = ViewModelProvider(this)[ActorsViewModel::class.java]

        initViews(view)


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

            actorsViewModel.getActorsForCurrentMovie()

            actorsViewModel.mutableActorList.observe(viewLifecycleOwner) {
                adapter?.setActors(it)
            }

            headImage?.load(movie.poster) {
                crossfade(true)
                placeholder(R.drawable.movie_placeholder)
                fallback(R.drawable.movie_placeholder)
                error(R.drawable.movie_placeholder)
            }


            val backBtn: TextView = view.findViewById(R.id.back_btn)
            backBtn.setOnClickListener {
                fragmentManager?.popBackStack()
            }

        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentMovie = arguments?.getParcelable(MOVIE_KEY)
        checkInternetConnection = InternetConnectionManager(requireContext())

        if (checkInternetConnection.isNetworkAvailable()) {
            scope.launch {
                ActorsRepository.loadActors(currentMovie?.id!!)

            }
        } else {
            Toast.makeText(context, getString(R.string.unable_load_cast), Toast.LENGTH_LONG)
                .show()
        }

    }


    override fun onPause() {
        super.onPause()
        actorsViewModel.clearActorList()
    }

    private fun initViews(view: View) {

        tvTitle = view.findViewById(R.id.tv_title)
        tvAge = view.findViewById(R.id.tv_age)
        tvGenres = view.findViewById(R.id.movie_genres)
        tvReviews = view.findViewById(R.id.tv_reviews)
        movieRating = view.findViewById(R.id.movie_rating_bar)
        tvStoryLine = view.findViewById(R.id.tv_description)
        headImage = view.findViewById(R.id.head_image)

        adapter = ActorsAdapter(requireContext())
        rvActors = view.findViewById(R.id.rv_actors)
        rvActors?.adapter = adapter
        rvActors?.setHasFixedSize(true)

    }

}

