package com.johnsondev.doboshacademyapp

import android.content.Context
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter(
    context: Context,
    private val clickListener: OnRecyclerItemClicked
) : RecyclerView.Adapter<MovieViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = Movie.movies.size

    fun getItem(position: Int): Movie = Movie.movies[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = inflater.inflate(R.layout.movie_rv_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener() {
            clickListener.onClick(Movie.movies[position])
        }
    }

}

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.movie_name)
    private val genre: TextView = view.findViewById(R.id.movie_genre)
    private val reviews: TextView = view.findViewById(R.id.movie_reviews)
    private val rating: RatingBar = view.findViewById(R.id.movie_rating)
    private val time: TextView = view.findViewById(R.id.movie_time)
    private val age: TextView = view.findViewById(R.id.movie_age)
    private val movieImg: ImageView = view.findViewById(R.id.movie_img)
    private val isFavorite: ImageView = view.findViewById(R.id.is_favorite_red_img)

    fun bind(movie: Movie) {
        name.text = movie.name
        genre.text = movie.genre
        reviews.text = "${movie.reviews} REVIEWS"
        rating.progress = movie.rating
        time.text = "${movie.time} MIN"
        age.text = "${movie.age}+"
        movieImg.setImageResource(movie.movieImg)
        isFavorite.visibility = if (movie.isFavorite) View.VISIBLE else View.GONE
    }

}

interface OnRecyclerItemClicked {
    fun onClick(movie: Movie)
}

