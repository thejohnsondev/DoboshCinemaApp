package com.johnsondev.doboshacademyapp.data.network.response

import com.johnsondev.doboshacademyapp.data.network.dto.ActorImageProfileDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ActorImagesResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("profiles")
    val profiles: List<ActorImageProfileDto>
)