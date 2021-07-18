package com.johnsondev.doboshacademyapp.data.network.response

import com.johnsondev.doboshacademyapp.data.network.dto.ActorMovieCreditDto
import com.johnsondev.doboshacademyapp.data.network.dto.MovieDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorMovieCreditsResponse(
    @SerialName("cast")
    val cast: List<MovieDto>,
    @SerialName("id")
    val id: Int
)