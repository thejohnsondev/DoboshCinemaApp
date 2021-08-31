package com.johnsondev.doboshacademyapp.data.network.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ProductionCountryDto(
    @SerialName("iso_3166_1")
    val countryCode: String?,
    @SerialName("name")
    val countryName: String?
): Parcelable