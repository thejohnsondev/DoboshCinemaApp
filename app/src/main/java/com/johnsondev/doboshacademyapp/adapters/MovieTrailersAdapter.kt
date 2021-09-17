package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.network.dto.MovieVideoDto
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

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

    private val trailerTitle: TextView = view.findViewById(R.id.trailer_title)
    private val ytPlayer: YouTubePlayerView = view.findViewById(R.id.yt_player)

    fun bind(trailer: MovieVideoDto) {
        trailerTitle.text = trailer.name
        ytPlayer.clipToOutline = true


        ytPlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(trailer.key, 0f)
                youTubePlayer.pause()
            }

        })

    }
}