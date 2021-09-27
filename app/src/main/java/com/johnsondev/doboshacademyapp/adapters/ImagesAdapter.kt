package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.network.dto.MovieImageDto
import com.johnsondev.doboshacademyapp.utilities.Constants.BACKDROP_PATH
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_BACKDROP
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_POSTER
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_PATH

class ImagesAdapter(
    context: Context,
    private val listType: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var imagesList: List<MovieImageDto> = listOf()

    override fun getItemViewType(position: Int): Int {
        return if (listType == ITEM_TYPE_POSTER) ITEM_TYPE_POSTER else ITEM_TYPE_BACKDROP
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val posterItemView = inflater.inflate(R.layout.poster_rv_item, parent, false)
        val backdropsItemView = inflater.inflate(R.layout.backdrop_rv_item, parent, false)
        return when (viewType) {
            ITEM_TYPE_POSTER -> PosterViewHolder(posterItemView)
            else -> BackdropViewHolder(backdropsItemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PosterViewHolder -> {
                holder.bind(imagesList[position])
            }
            is BackdropViewHolder -> {
                holder.bind(imagesList[position])
            }
        }
    }


    override fun getItemCount(): Int = imagesList.size

    fun setImagesList(list: List<MovieImageDto>) {
        imagesList = list
        notifyDataSetChanged()
    }
}

class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val posterImageView: ImageView = itemView.findViewById(R.id.iv_poster_rv_item)

    fun bind(image: MovieImageDto) {

        posterImageView.load("$POSTER_PATH${image.filePath}") {
            crossfade(true)
            error(R.drawable.ic_baseline_image_24)
            placeholder(R.drawable.ic_baseline_image_24)
            transformations(RoundedCornersTransformation(10f))
        }

    }

}

class BackdropViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val backdropImageView: ImageView = itemView.findViewById(R.id.iv_backdrop_rv_item)

    fun bind(image: MovieImageDto) {

        backdropImageView.load("$BACKDROP_PATH${image.filePath}") {
            crossfade(true)
            error(R.drawable.ic_baseline_image_24)
            placeholder(R.drawable.ic_baseline_image_24)
            transformations(RoundedCornersTransformation(10f))
        }

    }

}