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
    val id: Int,
    @SerialName("title")
    val title: String?,
    @SerialName("poster_path")
    val poster: String?,
    @SerialName("backdrop_path")
    val backdropImg: String?,
    @SerialName("runtime")
    var runtime: Int? = null,
    @SerialName("genres")
    var genres: @RawValue List<GenreDto>? = null,
    @SerialName("vote_average")
    val rating: Float?,
    @SerialName("vote_count")
    val voteCount: Int?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("adult")
    val adult: Boolean?,
    @SerialName("budget")
    var budget: Int?,
    @SerialName("revenue")
    var revenue: Int?,
    @SerialName("original_language")
    var origLanguage: String?,
    @SerialName("original_title")
    var origTitle: String?,
    @SerialName("production_companies")
    var productionCompanies: @RawValue List<ProductionCompanyDto>? = null,
    @SerialName("production_countries")
    var productionCountries: @RawValue List<ProductionCountryDto>? = null,
    @SerialName("release_date")
    var releaseDate: String?,
    @SerialName("status")
    var status: String?,
    @SerialName("tagline")
    var tagLine: String?
) : Parcelable