package com.johnsondev.doboshacademyapp.data.models.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Actor(
    val id: Int,
    val name: String,
    val picture: String?
) : Parcelable
