package com.johnsondev.doboshacademyapp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresListResponse(
    @SerialName("genres")
    val genres: List<GenreDto>
)