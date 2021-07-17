package com.johnsondev.doboshacademyapp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorDetailsDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String?,
    @SerialName("birthday")
    val birthDay: String?,
    @SerialName("deathday")
    val deathDay: String?,
    @SerialName("gender")
    val gender: Int?,
    @SerialName("biography")
    val biography: String?,
    @SerialName("popularity")
    val popularity: Double?,
    @SerialName("place_of_birth")
    val placeOfBirth: String?,
    @SerialName("profile_path")
    val profilePath: String?
)