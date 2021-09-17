package com.johnsondev.doboshacademyapp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultDto(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("media_type")
    val mediaType: String = ""
)