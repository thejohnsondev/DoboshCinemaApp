package com.johnsondev.doboshacademyapp.data.network.dto

import kotlinx.android.parcel.RawValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorMovieCreditDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String?,
    @SerialName("poster_path")
    val poster: String?,
    @SerialName("backdrop_path")
    val backdropImg: String?,
    @SerialName("vote_average")
    val rating: Float?,
    @SerialName("vote_count")
    val voteCount: Int?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("adult")
    val adult: Boolean?
)