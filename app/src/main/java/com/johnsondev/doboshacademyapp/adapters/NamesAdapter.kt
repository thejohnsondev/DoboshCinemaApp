package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.databinding.GenreRvItemBinding

class NamesAdapter(
    context: Context,
) : RecyclerView.Adapter<NameViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var namesList: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val itemView = inflater.inflate(R.layout.genre_rv_item, parent, false)
        return NameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        holder.bind(namesList[position])
    }

    override fun getItemCount(): Int = namesList.size

    fun setNamesList(genres: List<String>){
        namesList = genres
        notifyDataSetChanged()
    }
}

class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding by viewBinding(GenreRvItemBinding::bind)

    fun bind(name: String) {
        binding.popGenreName.text = name
    }

}
