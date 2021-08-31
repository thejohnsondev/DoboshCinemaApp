package com.johnsondev.doboshacademyapp.data.network.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
@Parcelize
data class MovieDto(
    @SerialName("id")
    var id: Int,
    @SerialName("title")
    var title: String?,
    @SerialName("poster_path")
    var poster: String?,
    @SerialName("backdrop_path")
    var backdropImg: String?,
    @SerialName("runtime")
    var runtime: Int? = null,
    @SerialName("genres")
    var genres: @RawValue List<GenreDto>? = null,
    @SerialName("vote_average")
    var rating: Float?,
    @SerialName("vote_count")
    var voteCount: Int?,
    @SerialName("overview")
    var overview: String?,
    @SerialName("adult")
    var adult: Boolean?,
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