package com.johnsondev.doboshacademyapp.model.dto

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class ActorDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val profileImg: String?
)