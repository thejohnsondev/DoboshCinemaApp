package com.johnsondev.doboshacademyapp.data.network.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MovieVideoDto(
    @SerialName("name")
    val name: String,
    @SerialName("key")
    val key: String,
    @SerialName("type")
    val type: String
): Parcelable