package com.johnsondev.doboshacademyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.johnsondev.doboshacademyapp.model.Movie

class MoviesAdapter(
    private val context: Context,
    private val clickListener: OnRecyclerItemClicked,
    private val moviesList: List<Movie>
) : RecyclerView.Adapter<MovieViewHolder>() {

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
}

class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.movie_name)
    private val genre: TextView = view.findViewById(R.id.movie_genres)
    private val reviews: TextView = view.findViewById(R.id.movie_reviews)
    private val rating: RatingBar = view.findViewById(R.id.movie_rating)
    private val time: TextView = view.findViewById(R.id.movie_time)
    private val age: TextView = view.findViewById(R.id.tv_age)
    private val movieImg: ImageView = view.findViewById(R.id.movie_img)

    fun bind(movie: Movie) {

        val movieReviews: String = view.context.getString(R.string.reviews, movie.reviewCount)
        val movieAge: String = view.context.getString(R.string.plus, movie.pgAge)
        val movieRunningTime: String =
            view.context.getString(R.string.running_time, movie.runningTime)

        name.text = movie.title
        genre.text = movie.genres.joinToString { it.name }
        reviews.text = movieReviews
        rating.progress = movie.rating * 2
        time.text = movieRunningTime
        age.text = movieAge

        Glide.with(itemView)
            .load(movie.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(movieImg)

        movieImg.clipToOutline = true
    }

}

interface OnRecyclerItemClicked {
    fun onClick(movie: Movie)
}

