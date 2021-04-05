package com.johnsondev.doboshacademyapp.model.response

import com.johnsondev.doboshacademyapp.model.dto.MovieDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviePopularResponse(
    val page: Long,
    val results: List<MovieDto>,

    @SerialName("total_pages")
    val totalPages: Long,

    @SerialName("total_results")
    val totalResults: Long
)
// TODO: 05.04.2021 create one movieResponse for all type of requests