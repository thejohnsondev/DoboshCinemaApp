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
import coil.transform.RoundedCornersTransformation
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ITEM_DEFAULT
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ITEM_LARGE
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ITEM_MINI

class MoviesAdapter(
    private val context: Context,
    private val clickListener: OnMovieItemClickListener,
    private val itemType: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var moviesList: List<Movie> = listOf()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = moviesList.size

    private fun getItem(position: Int): Movie = moviesList[position]

    override fun getItemViewType(position: Int): Int {
        return when (itemType) {
            MOVIE_ITEM_MINI -> MOVIE_ITEM_MINI
            MOVIE_ITEM_DEFAULT -> MOVIE_ITEM_DEFAULT
            else -> MOVIE_ITEM_LARGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        return when (viewType) {
            MOVIE_ITEM_MINI -> {
                itemView = inflater.inflate(R.layout.movie_rv_item_mini, parent, false)
                MovieViewHolderMini(itemView)
            }
            MOVIE_ITEM_DEFAULT -> {
                itemView = inflater.inflate(R.layout.movie_rv_item, parent, false)
                MovieViewHolderDefault(itemView, context)
            }
            else -> {
                itemView = inflater.inflate(R.layout.movie_rv_item_horizontal, parent, false)
                MovieViewHolderLarge(itemView, context)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolderDefault -> {
                holder.bind(getItem(position))
                holder.itemView.setOnClickListener {
                    clickListener.onClick(moviesList[position])
                }
            }
            is MovieViewHolderLarge -> {
                holder.bind(getItem(position))
                holder.itemView.setOnClickListener {
                    clickListener.onClick(moviesList[position])
                }
            }
            is MovieViewHolderMini -> {
                holder.bind(getItem(position))
                holder.itemView.setOnClickListener {
                    clickListener.onClick(moviesList[position])
                }
            }
        }

    }

    fun setMovies(movies: List<Movie>) {
        moviesList = movies
        notifyDataSetChanged()
    }
}

class MovieViewHolderDefault(private val view: View, private val context: Context) :
    RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.movie_title)
    private val reviews: TextView = view.findViewById(R.id.movie_reviews)
    private val rating: RatingBar = view.findViewById(R.id.movie_rating)
    private val movieImg: ImageView = view.findViewById(R.id.movie_img)
    private val movieMask: ImageView = view.findViewById(R.id.movie_mask)

    fun bind(movie: Movie) {
        itemView.transitionName =
            context.getString(R.string.shared_element_container_with_id, movie.id.toString())

        val movieReviews: String = view.context.getString(R.string.reviews, movie.numberOfRatings)

        name.text = movie.title
        reviews.text = movieReviews
        rating.progress = (movie.ratings * 2).toInt()

        movieImg.load(movie.poster) {
            crossfade(true)
            placeholder(R.drawable.movie_placeholder)
            fallback(R.drawable.movie_placeholder)
            error(R.drawable.movie_placeholder)
        }
        movieImg.clipToOutline = true
        movieMask.clipToOutline = true
    }

}

class MovieViewHolderLarge(private val view: View, private val context: Context) :
    RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.movie_title)
    private val reviews: TextView = view.findViewById(R.id.movie_reviews)
    private val rating: RatingBar = view.findViewById(R.id.movie_rating)
    private val movieImg: ImageView = view.findViewById(R.id.movie_img)

    fun bind(movie: Movie) {
        itemView.transitionName =
            context.getString(R.string.shared_element_container_with_id, movie.id.toString())

        val movieReviews: String = view.context.getString(R.string.reviews, movie.numberOfRatings)

        name.text = movie.title
        reviews.text = movieReviews
        rating.progress = (movie.ratings * 2).toInt()

        movieImg.load(movie.poster) {
            crossfade(true)
            placeholder(R.drawable.movie_placeholder)
            fallback(R.drawable.movie_placeholder)
            error(R.drawable.movie_placeholder)
        }
        movieImg.clipToOutline = true
    }
}

class MovieViewHolderMini(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name: TextView = itemView.findViewById(R.id.movie_title)
    private val movieImg: ImageView = itemView.findViewById(R.id.movie_img)

    fun bind(movie: Movie) {
        name.text = movie.title

        movieImg.load(movie.poster) {
            RoundedCornersTransformation(10f)
            crossfade(true)
            placeholder(R.drawable.movie_placeholder)
            fallback(R.drawable.movie_placeholder)
            error(R.drawable.movie_placeholder)
        }
        movieImg.clipToOutline = true
    }
}

interface OnMovieItemClickListener {
    fun onClick(movie: Movie)
}

