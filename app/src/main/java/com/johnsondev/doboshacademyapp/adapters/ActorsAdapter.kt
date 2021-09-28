package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.databinding.ActorRvItemBinding
import com.johnsondev.doboshacademyapp.databinding.ActorRvItemHorizontalBinding
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_HORIZONTAL
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_MINI

class ActorsAdapter(
    context: Context,
    private val clickListener: OnActorItemClickListener,
    private val itemType: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var actorsList: List<Actor> = listOf()


    override fun getItemViewType(position: Int): Int {
        return if (itemType == ITEM_TYPE_MINI) ITEM_TYPE_MINI else ITEM_TYPE_HORIZONTAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemViewMini = inflater.inflate(R.layout.actor_rv_item, parent, false)
        val itemViewHorizontal = inflater.inflate(R.layout.actor_rv_item_horizontal, parent, false)
        return when (viewType) {
            ITEM_TYPE_MINI -> ActorViewHolderMini(itemViewMini)
            else -> ActorViewHolderHorizontal(itemViewHorizontal)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ActorViewHolderMini -> {
                holder.bind(actorsList[position])
                holder.itemView.setOnClickListener {
                    clickListener.onClick(actorsList[position])
                }
            }
            is ActorViewHolderHorizontal -> {
                holder.bind(actorsList[position])
                holder.itemView.setOnClickListener {
                    clickListener.onClick(actorsList[position])
                }
            }
        }

    }

    override fun getItemCount(): Int = actorsList.size

    fun setActors(actors: List<Actor>) {
        actorsList = actors
        notifyDataSetChanged()
    }
}

class ActorViewHolderMini(view: View) : RecyclerView.ViewHolder(view) {

    private val binding by viewBinding(ActorRvItemBinding::bind)

    fun bind(actor: Actor) {
        binding.tvActorName.text = actor.name

        binding.actorImg.load(actor.picture) {
            crossfade(true)
            placeholder(R.drawable.ic_baseline_person_24)
            fallback(R.drawable.ic_baseline_person_24)
            error(R.drawable.ic_baseline_person_24)
        }
        binding.actorImg.clipToOutline = true
    }

}

class ActorViewHolderHorizontal(view: View) : RecyclerView.ViewHolder(view) {

    private val binding by viewBinding(ActorRvItemHorizontalBinding::bind)

    fun bind(actor: Actor) {
        binding.tvActorNameHorizon.text = actor.name

        binding.actorImgHorizon.load(actor.picture) {
            crossfade(true)
            placeholder(R.drawable.ic_baseline_person_24)
            error(R.drawable.ic_baseline_person_24)
        }

        binding.actorImgHorizon.clipToOutline = true
    }
}

interface OnActorItemClickListener {
    fun onClick(actor: Actor)
}