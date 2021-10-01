package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.network.dto.MovieVideoDto
import com.johnsondev.doboshacademyapp.databinding.MovieTrailerRvItemBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class MovieTrailersAdapter(context: Context) : RecyclerView.Adapter<MovieTrailerViewHolder>() {

    private var trailersList: List<MovieVideoDto> = listOf()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTrailerViewHolder {
        val itemView = inflater.inflate(R.layout.movie_trailer_rv_item, parent, false)
        return MovieTrailerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieTrailerViewHolder, position: Int) {
        holder.bind(trailersList[position])
    }

    override fun getItemCount(): Int = trailersList.size

    fun setTrailers(trailers: List<MovieVideoDto>) {
        trailersList = trailers
        notifyDataSetChanged()
    }

}

class MovieTrailerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding by viewBinding(MovieTrailerRvItemBinding::bind)

    fun bind(trailer: MovieVideoDto) {
        binding.trailerTitle.text = trailer.name
        binding.ytPlayer.clipToOutline = true

        binding.ytPlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(trailer.key, 0f)
                youTubePlayer.pause()
            }
        })
    }
}