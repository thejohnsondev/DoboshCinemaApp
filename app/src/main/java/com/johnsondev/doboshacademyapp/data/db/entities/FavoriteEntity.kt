package com.johnsondev.doboshacademyapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.johnsondev.doboshacademyapp.utilities.Constants.TABLE_FAVORITES

@Entity(tableName = TABLE_FAVORITES)
data class FavoriteEntity(
    @PrimaryKey
    val entityId: Int,
    val entityType: String
)
