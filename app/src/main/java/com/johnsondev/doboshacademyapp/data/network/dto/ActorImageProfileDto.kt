package com.johnsondev.doboshacademyapp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorImageProfileDto(
    @SerialName("aspect_ratio")
    val imageRatio: Double? = 0.0,
    @SerialName("file_path")
    val imagePath: String = "",
    @SerialName("height")
    val height: Int? = 0,
    @SerialName("width")
    val width: Int? = 0
)