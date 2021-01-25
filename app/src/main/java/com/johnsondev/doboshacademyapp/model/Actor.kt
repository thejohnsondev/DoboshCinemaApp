package com.johnsondev.doboshacademyapp.model

import java.io.Serializable

data class Actor(
    val id: Int,
    val name: String,
    val imageUrl: String,
) : Serializable