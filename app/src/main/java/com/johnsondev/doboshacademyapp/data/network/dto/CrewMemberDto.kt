package com.johnsondev.doboshacademyapp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrewMemberDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val profileImg: String?,
    @SerialName("job")
    val job: String
)