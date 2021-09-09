package com.johnsondev.doboshacademyapp.data.network.response

import com.johnsondev.doboshacademyapp.data.network.dto.MovieDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesListResponse(
    val page: Long,
    val results: List<MovieDto>,

    @SerialName("total_pages")
    val totalPages: Long,

    @SerialName("total_results")
    val totalResults: Long,
)