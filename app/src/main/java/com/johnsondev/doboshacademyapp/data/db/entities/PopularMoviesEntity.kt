package com.johnsondev.doboshacademyapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popularMovies")
data class PopularMoviesEntity(

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
    val genresId: String,
    val actorsId: String

)