package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.network.dto.ActorImageProfileDto
import com.johnsondev.doboshacademyapp.databinding.ActorImageRvItemBinding
import com.johnsondev.doboshacademyapp.utilities.Constants

class ActorImagesAdapter(
    context: Context,
    private val clickListener: OnImageClickListener
) : RecyclerView.Adapter<ActorImageViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var images: List<ActorImageProfileDto> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorImageViewHolder {
        val itemView = inflater.inflate(R.layout.actor_image_rv_item, parent, false)
        return ActorImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ActorImageViewHolder, position: Int) {
        holder.bind(images[position])
        holder.itemView.setOnClickListener {
            clickListener.onClick(images[position])
        }
    }

    override fun getItemCount(): Int = images.size

    fun setActorImages(images: List<ActorImageProfileDto>) {
        this.images = images
        notifyDataSetChanged()
    }

}

class ActorImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding by viewBinding(ActorImageRvItemBinding::bind)

    fun bind(actorImage: ActorImageProfileDto) {

        binding.ivActorImg.load("${Constants.POSTER_PATH}${actorImage.imagePath}") {
            crossfade(true)
            placeholder(R.drawable.ic_baseline_image_24)
            fallback(R.drawable.ic_baseline_image_24)
        }
        binding.ivActorImg.clipToOutline = true
    }
}


interface OnImageClickListener {
    fun onClick(actorImage: ActorImageProfileDto)
}
