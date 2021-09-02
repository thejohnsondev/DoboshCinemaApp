package com.johnsondev.doboshacademyapp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieImageDto(
    @SerialName("aspect_ratio")
    val aspectRatio: Double,
    @SerialName("file_path")
    val filePath: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int
)