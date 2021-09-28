package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.models.base.CrewMember
import com.johnsondev.doboshacademyapp.databinding.CrewMemberRvItemBinding

class CrewAdapter(context: Context) : RecyclerView.Adapter<CrewMemberViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var crewList: List<CrewMember> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewMemberViewHolder {
        val view = inflater.inflate(R.layout.crew_member_rv_item, parent, false)
        return CrewMemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: CrewMemberViewHolder, position: Int) {
        holder.bind(crewList[position])
    }

    override fun getItemCount(): Int = crewList.size

    fun setCrewList(list: List<CrewMember>) {
        crewList = list
        notifyDataSetChanged()
    }

}

class CrewMemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding by viewBinding(CrewMemberRvItemBinding::bind)

    fun bind(crewMember: CrewMember) {
        binding.crewMemberName.text = crewMember.name
        binding.crewMemberJob.text = crewMember.job
    }

}