package com.johnsondev.doboshacademyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.johnsondev.doboshacademyapp.model.Actor
import com.johnsondev.doboshacademyapp.model.Movie


class ActorsAdapter(
    private val actorsList: List<Actor>,
    context: Context
) : RecyclerView.Adapter<ActorViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val itemView = inflater.inflate(R.layout.actors_rv_item, parent, false)
        return ActorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(actorsList[position])
    }

    override fun getItemCount(): Int = actorsList.size

}

class ActorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val actorImg: ImageView = view.findViewById(R.id.actor_img)
    private val actorName: TextView = view.findViewById(R.id.tv_actor_name)

    fun bind(actor: Actor) {
        actorName.text = actor.name

        Glide.with(itemView)
            .load(actor.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(actorImg)

        actorImg.clipToOutline = true
    }

}