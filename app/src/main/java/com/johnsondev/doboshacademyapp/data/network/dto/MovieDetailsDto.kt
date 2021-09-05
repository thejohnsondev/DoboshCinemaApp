package com.johnsondev.doboshacademyapp.data.network.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MovieDetailsDto(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("title")
    val title: String = "",
    @SerialName("poster_path")
    val poster: String = "",
    @SerialName("backdrop_path")
    val backdropImg: String = "",
    @SerialName("runtime")
    var runtime: Int = 0,
    @SerialName("genres")
    var genres: @RawValue List<GenreDto> = emptyList(),
    @SerialName("vote_average")
    val rating: Float = 0f,
    @SerialName("vote_count")
    val voteCount: Int = 0,
    @SerialName("overview")
    val overview: String = "",
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("budget")
    var budget: Int = 0,
    @SerialName("revenue")
    var revenue: Int = 0,
    @SerialName("original_language")
    var origLanguage: String = "",
    @SerialName("original_title")
    var origTitle: String = "",
    @SerialName("production_companies")
    var productionCompanies: @RawValue List<ProductionCompanyDto> = emptyList(),
    @SerialName("production_countries")
    var productionCountries: @RawValue List<ProductionCountryDto> = emptyList(),
    @SerialName("release_date")
    var releaseDate: String = "",
    @SerialName("status")
    var status: String = "",
    @SerialName("tagline")
    var tagLine: String = ""
) : Parcelable