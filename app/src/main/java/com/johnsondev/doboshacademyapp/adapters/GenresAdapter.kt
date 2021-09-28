package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.models.base.Genre
import com.johnsondev.doboshacademyapp.databinding.GenreRvItemBinding

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

    fun setGenresList(genres: List<Genre>) {
        genresList = genres
        notifyDataSetChanged()
    }
}

class PopGenresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding by viewBinding(GenreRvItemBinding::bind)

    fun bind(genre: Genre) {
        binding.popGenreName.text = genre.name
    }
}

interface OnGenreClickListener {
    fun onClick(genre: Genre)
}