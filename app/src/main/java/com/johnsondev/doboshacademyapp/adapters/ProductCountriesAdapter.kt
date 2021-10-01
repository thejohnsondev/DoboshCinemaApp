package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.models.base.ProductionCountry
import com.johnsondev.doboshacademyapp.databinding.CountyRvItemBinding

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

    fun setCountries(list: List<ProductionCountry>) {
        countriesList = list
        notifyDataSetChanged()
    }
}

class ProductCountriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding by viewBinding(CountyRvItemBinding::bind)

    fun bind(productionCountry: ProductionCountry) {
        binding.tvCountryName.text = productionCountry.countryName
    }
}