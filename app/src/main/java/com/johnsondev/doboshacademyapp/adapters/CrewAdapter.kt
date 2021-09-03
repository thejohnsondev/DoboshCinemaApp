package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.CrewMember

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

    private val name: TextView = itemView.findViewById(R.id.crew_member_name)
    private val job: TextView = itemView.findViewById(R.id.crew_member_job)

    fun bind(crewMember: CrewMember) {
        name.text = crewMember.name
        job.text = crewMember.job
    }

}