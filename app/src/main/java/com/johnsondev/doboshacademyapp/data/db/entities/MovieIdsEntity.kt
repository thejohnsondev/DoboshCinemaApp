package com.johnsondev.doboshacademyapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieIds")
data class MovieIdsEntity(
    @PrimaryKey
    var _id: Int = -1,
    val listType: String?,
    val movieIdsList: String?
)