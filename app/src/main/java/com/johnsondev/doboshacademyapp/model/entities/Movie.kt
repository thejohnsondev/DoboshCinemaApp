package com.johnsondev.doboshacademyapp.model.entities
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable

@Parcelize
data class Movie(
    val id: Int,
    val pgAge: Int,
    val title: String,
    val genres: @RawValue List<Genre>,
    val runningTime: Int,
    val reviewCount: Int,
    val isLiked: Boolean,
    val rating: Int,
    val imageUrl: String,
    val detailImageUrl: String,
    val storyLine: String,
    val actors: @RawValue List<Actor>,
) : Serializable, Parcelable