package com.johnsondev.doboshacademyapp.data.network.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ActorDetailsDto(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("birthday")
    val birthDay: String = "",
    @SerialName("deathday")
    val deathDay: String = "",
    @SerialName("gender")
    val gender: Int = 0,
    @SerialName("biography")
    val biography: String = "",
    @SerialName("popularity")
    val popularity: Double = 0.0,
    @SerialName("place_of_birth")
    val placeOfBirth: String = "",
    @SerialName("profile_path")
    val profilePath: String = "",
    @SerialName("also_known_as")
    val alsoKnownAs: List<String> = emptyList(),
    @SerialName("known_for_department")
    val department: String = ""
): Parcelable