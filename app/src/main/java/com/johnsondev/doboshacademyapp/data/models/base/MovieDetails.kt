package com.johnsondev.doboshacademyapp.data.models.base

import com.johnsondev.doboshacademyapp.data.network.dto.ProductionCompanyDto
import com.johnsondev.doboshacademyapp.data.network.dto.ProductionCountryDto

data class MovieDetails(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val poster: String = "",
    val backdrop: String = "",
    val ratings: Float = 0f,
    val numberOfRatings: Int = 0,
    val minimumAge: Int = 0,
    val runtime: Int = 0,
    val genres: List<Genre> = emptyList(),
    val actors: List<Actor> = emptyList(),
    val budget: Int = 0,
    val revenue: Int = 0,
    val origLanguage: String = "",
    val origTitle: String = "",
    val productionCompanies: List<ProductionCompanyDto> = emptyList(),
    val productionCountries: List<ProductionCountryDto> = emptyList(),
    val releaseDate: String = "",
    val status: String = "",
    val tagLine: String = ""
)