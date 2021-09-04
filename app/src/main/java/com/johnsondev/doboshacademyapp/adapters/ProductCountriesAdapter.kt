package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.ProductionCountry
import com.johnsondev.doboshacademyapp.data.network.dto.ProductionCountryDto

class ProductCountriesAdapter(context: Context) :
    RecyclerView.Adapter<ProductCountriesViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var countriesList: List<ProductionCountry> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCountriesViewHolder {
        val view = inflater.inflate(R.layout.county_rv_item, parent, false)
        return ProductCountriesViewHolder(view)
    }


    override fun onBindViewHolder(holder: ProductCountriesViewHolder, position: Int) {
        holder.bind(countriesList[position])
    }


    override fun getItemCount(): Int = countriesList.size

    fun setCountries(list: List<ProductionCountry>){
        countriesList = list
        notifyDataSetChanged()
    }
}

class ProductCountriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvCountryName: TextView = itemView.findViewById(R.id.tv_country_name)
    private val ivCountyFrag: ImageView = itemView.findViewById(R.id.iv_country_flag)

    fun bind(productionCountry: ProductionCountry) {
        tvCountryName.text = productionCountry.countryName

    }

}