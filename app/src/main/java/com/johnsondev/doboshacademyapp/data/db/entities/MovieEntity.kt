package com.johnsondev.doboshacademyapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.johnsondev.doboshacademyapp.data.db.MoviesContract
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Genre

@Entity(tableName = MoviesContract.MoviesColumns.TABLE_NAME)
data class MovieEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
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

)