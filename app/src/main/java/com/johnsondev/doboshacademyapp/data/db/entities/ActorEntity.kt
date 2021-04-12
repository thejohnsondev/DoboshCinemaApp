package com.johnsondev.doboshacademyapp.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.johnsondev.doboshacademyapp.data.db.MoviesContract

@Entity(
    tableName = MoviesContract.ActorColumns.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("filmId"),
        onDelete = CASCADE
    )]
)
data class ActorEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val picture: String?,
    val filmID: Int
)