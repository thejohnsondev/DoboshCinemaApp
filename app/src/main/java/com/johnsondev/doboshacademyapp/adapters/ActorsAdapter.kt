package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.models.Actor

class ActorsAdapter(
    context: Context
) : RecyclerView.Adapter<ActorViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var actorsList: List<Actor> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val itemView = inflater.inflate(R.layout.actors_rv_item, parent, false)
        return ActorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(actorsList[position])
    }

    override fun getItemCount(): Int = actorsList.size

    fun setActors(actors: List<Actor>) {
        actorsList = actors
        notifyDataSetChanged()
    }
}

class ActorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val actorImg: ImageView = view.findViewById(R.id.actor_img)
    private val actorName: TextView = view.findViewById(R.id.tv_actor_name)

    fun bind(actor: Actor) {
        actorName.text = actor.name

        actorImg.load(actor.picture) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            fallback(R.drawable.ic_launcher_foreground)
            error(R.drawable.ic_baseline_person_24)
        }

        actorImg.clipToOutline = true
    }

}