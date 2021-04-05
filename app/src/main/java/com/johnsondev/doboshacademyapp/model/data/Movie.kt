package com.johnsondev.doboshacademyapp.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int?,
    val genres: List<Genre>? = emptyList(),
    val actors: List<Actor> = emptyList()
) : Parcelable