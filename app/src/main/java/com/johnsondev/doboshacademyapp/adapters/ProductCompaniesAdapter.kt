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
import com.johnsondev.doboshacademyapp.data.network.dto.ProductionCompanyDto
import com.johnsondev.doboshacademyapp.data.network.dto.ProductionCountryDto
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_PATH

class ProductCompaniesAdapter(context: Context) :
    RecyclerView.Adapter<ProductCompanyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var companiesList: List<ProductionCompanyDto> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCompanyViewHolder {
        val view = inflater.inflate(R.layout.company_rv_item, parent, false)
        return ProductCompanyViewHolder(view)
    }


    override fun onBindViewHolder(holder: ProductCompanyViewHolder, position: Int) {
        holder.bind(companiesList[position])
    }


    override fun getItemCount(): Int = companiesList.size

    fun setCompanies(list: List<ProductionCompanyDto>) {
        companiesList = list
        notifyDataSetChanged()
    }
}

class ProductCompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvCompanyName: TextView = itemView.findViewById(R.id.tv_company_name)
    private val ivCompanyLogo: ImageView = itemView.findViewById(R.id.iv_company_logo)

    fun bind(productionCompany: ProductionCompanyDto) {
        tvCompanyName.text = productionCompany.name

        ivCompanyLogo.load("$POSTER_PATH${productionCompany.logoPath}") {
            crossfade(true)
        }
    }

}