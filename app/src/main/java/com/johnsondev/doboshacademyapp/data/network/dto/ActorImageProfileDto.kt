package com.johnsondev.doboshacademyapp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorImageProfileDto(
    @SerialName("aspect_ratio")
    val imageRatio: Double,
    @SerialName("file_path")
    val imagePath: String,
    @SerialName("height")
    val height: Int,
    @SerialName("width")
    val width: Int
)