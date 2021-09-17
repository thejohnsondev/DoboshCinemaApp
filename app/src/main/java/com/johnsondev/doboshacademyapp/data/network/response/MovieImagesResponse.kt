package com.johnsondev.doboshacademyapp.data.network.response

import com.johnsondev.doboshacademyapp.data.network.dto.MovieImageDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MovieImagesResponse(
    @SerialName("backdrops")
    val backdrops: List<MovieImageDto>,
    @SerialName("posters")
    val posters: List<MovieImageDto>
)