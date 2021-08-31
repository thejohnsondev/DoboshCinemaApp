package com.johnsondev.doboshacademyapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val poster: String = "",
    val backdrop: String? = "",
    val ratings: Float = 0f,
    val numberOfRatings: Int = 0,
    val minimumAge: Int = 0,
    val runtime: Int? = 0,
    val genres: List<Genre>? = emptyList(),
    val actors: List<Actor> = emptyList()
) : Parcelable