package com.johnsondev.doboshacademyapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CrewMember(
    val id: Int,
    val name: String,
    val picture: String,
    val job: String
): Parcelable