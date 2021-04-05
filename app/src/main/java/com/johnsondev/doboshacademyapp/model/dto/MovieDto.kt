package com.johnsondev.doboshacademyapp.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
@Parcelize
data class MovieDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val poster: String,
    @SerialName("backdrop_path")
    val backdropImg: String,
    @SerialName("runtime")
    var runtime: Int? = null,
    @SerialName("genres")
    var genres: @RawValue List<GenreDto>? = null,
    @SerialName("vote_average")
    val rating: Float,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("overview")
    val overview: String,
    @SerialName("adult")
    val adult: Boolean
) : Parcelable