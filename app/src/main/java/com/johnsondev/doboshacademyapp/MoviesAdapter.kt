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
        private val clickListener: OnRecyclerItemClicked
) : RecyclerView.Adapter<MovieViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = MovieRepository.moviesList.size

    private fun getItem(position: Int): Movie = MovieRepository.moviesList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = inflater.inflate(R.layout.movie_rv_item, parent, false)
        return MovieViewHolder(itemView, context)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener() {
            clickListener.onClick(MovieRepository.moviesList[position])
        }
    }
}

class MovieViewHolder(view: View, context: Context) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.movie_name)
    private val genre: TextView = view.findViewById(R.id.movie_genres)
    private val reviews: TextView = view.findViewById(R.id.movie_reviews)
    private val rating: RatingBar = view.findViewById(R.id.movie_rating)
    private val time: TextView = view.findViewById(R.id.movie_time)
    private val age: TextView = view.findViewById(R.id.tv_age)
    private val movieImg: ImageView = view.findViewById(R.id.movie_img)

    private val strReviews: String = context.getString(R.string.reviews)
    private val strMin: String = context.getString(R.string.min)
    private val strPlus: String = context.getString(R.string.plus)

    fun bind(movie: Movie) {
        name.text = movie.title
        genre.text = movie.genres.joinToString { it.name }
        reviews.text = "${movie.reviewCount} $strReviews"
        rating.progress = movie.rating*2
        time.text = "${movie.runningTime} $strMin"
        age.text = "${movie.pgAge}$strPlus"

        Glide.with(itemView)
            .load(movie.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(movieImg)

        movieImg.clipToOutline = true
    }
    companion object{

    }
}

interface OnRecyclerItemClicked {
    fun onClick(movie: Movie)
}

