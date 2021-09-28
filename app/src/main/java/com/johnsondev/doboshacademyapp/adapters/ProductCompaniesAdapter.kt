package com.johnsondev.doboshacademyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.network.dto.ProductionCompanyDto
import com.johnsondev.doboshacademyapp.databinding.CompanyRvItemBinding
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

    private val binding by viewBinding(CompanyRvItemBinding::bind)

    fun bind(productionCompany: ProductionCompanyDto) {
        binding.tvCompanyName.text = productionCompany.name

        binding.ivCompanyLogo.load("$POSTER_PATH${productionCompany.logoPath}") {
            crossfade(true)
        }
    }

}