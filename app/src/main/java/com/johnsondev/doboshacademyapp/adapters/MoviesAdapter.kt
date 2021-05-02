package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.models.Movie

class MoviesAdapter(
    private val context: Context,
    private val clickListener: OnRecyclerItemClicked,
) : RecyclerView.Adapter<MovieViewHolder>() {

    private var moviesList: List<Movie> = listOf()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = moviesList.size
    private fun getItem(position: Int): Movie = moviesList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = inflater.inflate(R.layout.movie_rv_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener() {
            clickListener.onClick(moviesList[position])
        }
    }

    fun setMovies(movies: List<Movie>) {
        moviesList = movies
        notifyDataSetChanged()
    }
}

class MovieViewHolder(private val view: View) :
    RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.movie_name)
    private val genre: TextView = view.findViewById(R.id.movie_genres)
    private val reviews: TextView = view.findViewById(R.id.movie_reviews)
    private val rating: RatingBar = view.findViewById(R.id.movie_rating)
    private val time: TextView = view.findViewById(R.id.movie_time)
    private val age: TextView = view.findViewById(R.id.tv_age)
    private val movieImg: ImageView = view.findViewById(R.id.movie_img)

    fun bind(movie: Movie) {

        val movieReviews: String = view.context.getString(R.string.reviews, movie.numberOfRatings)
        val movieAge: String = view.context.getString(R.string.plus, movie.minimumAge)
        val movieRunningTime: String =
            view.context.getString(R.string.running_time, movie.runtime)

        name.text = movie.title
        genre.text = movie.genres?.joinToString { it.name }
        reviews.text = movieReviews
        rating.progress = (movie.ratings * 2).toInt()
        time.text = movieRunningTime
        age.text = movieAge

        movieImg.load(movie.poster) {
            crossfade(true)
            placeholder(R.drawable.movie_placeholder)
            fallback(R.drawable.movie_placeholder)
            error(R.drawable.movie_placeholder)
        }
        movieImg.clipToOutline = true
    }

}

interface OnRecyclerItemClicked {
    fun onClick(movie: Movie)
}

