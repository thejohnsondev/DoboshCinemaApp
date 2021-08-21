package com.johnsondev.doboshacademyapp.data.network.response

import com.johnsondev.doboshacademyapp.data.network.dto.MovieVideoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideosResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("results")
    val results: List<MovieVideoDto>
)