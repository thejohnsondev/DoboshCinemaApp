package com.johnsondev.doboshacademyapp.data.network.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
@Parcelize
data class MovieDto(
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
    val rating: Float = 0f ,
    @SerialName("vote_count")
    val voteCount: Int = 0,
    @SerialName("overview")
    val overview: String = "",
    @SerialName("adult")
    val adult: Boolean = false
) : Parcelable