package com.johnsondev.doboshacademyapp.model.response

import com.johnsondev.doboshacademyapp.model.dto.ActorDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("cast")
    val cast: List<ActorDto>,
)
