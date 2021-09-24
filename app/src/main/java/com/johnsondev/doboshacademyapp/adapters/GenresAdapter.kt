package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.models.base.Genre

class GenresAdapter(
    context: Context,
    private val clickListener: OnGenreClickListener
) : RecyclerView.Adapter<PopGenresViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var genresList: List<Genre> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopGenresViewHolder {
        val itemView = inflater.inflate(R.layout.genre_rv_item, parent, false)
        return PopGenresViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: PopGenresViewHolder, position: Int) {
        holder.bind(genresList[position])
        holder.itemView.setOnClickListener {
            clickListener.onClick(genresList[position])
        }
    }

    override fun getItemCount(): Int = genresList.size

    fun setGenresList(genres: List<Genre>){
        genresList = genres
        notifyDataSetChanged()
    }
}

class PopGenresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val genreName: TextView = itemView.findViewById(R.id.pop_genre_name)

    fun bind(genre: Genre) {
        genreName.text = genre.name
    }

}

interface OnGenreClickListener {
    fun onClick(genre: Genre)
}