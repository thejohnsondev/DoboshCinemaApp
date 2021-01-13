package com.johnsondev.doboshacademyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.johnsondev.doboshacademyapp.model.Movie

class FragmentMoviesDetails : Fragment() {

    private var movieId: Int? = null
    private var currentMovie: Movie? = null

    private var tvTitle: TextView? = null
    private var tvAge: TextView? = null
    private var tvGenres: TextView? = null
    private var tvReviews: TextView? = null
    private var movieRating: RatingBar? = null
    private var tvStoryLine: TextView? = null
    private var headImage: ImageView? = null

    private var rvActors: RecyclerView? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)

        movieId = arguments?.getInt(MOVIE_KEY)
        currentMovie = MovieRepository.moviesList.find { it.id == movieId }

        tvTitle = view.findViewById(R.id.tv_title)
        tvAge = view.findViewById(R.id.tv_age)
        tvGenres = view.findViewById(R.id.movie_genres)
        tvReviews = view.findViewById(R.id.tv_reviews)
        movieRating = view.findViewById(R.id.movie_rating_bar)
        tvStoryLine = view.findViewById(R.id.tv_description)
        headImage = view.findViewById(R.id.head_image)

        rvActors = view.findViewById(R.id.rv_actors)
        rvActors?.adapter = ActorsAdapter(currentMovie!!, context!!)

        tvTitle?.text = currentMovie?.title
        tvAge?.text = "${currentMovie?.pgAge}${getString(R.string.plus)}"
        tvGenres?.text = currentMovie?.genres?.joinToString { it.name }
        tvReviews?.text = "${currentMovie?.reviewCount} ${getString(R.string.reviews)}"
        movieRating?.progress = currentMovie?.rating!! * 2
        tvStoryLine?.text = currentMovie?.storyLine

        Glide.with(context!!)
                .load(currentMovie!!.detailImageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(headImage!!)

        val backBtn: TextView = view.findViewById(R.id.back_btn)
        backBtn.setOnClickListener() {
            fragmentManager?.popBackStack()
        }

        return view
    }

    companion object {
        const val MOVIE_KEY = "movie key"
    }

}