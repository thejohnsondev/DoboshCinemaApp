package com.johnsondev.doboshacademyapp.model

import android.content.Context
import com.johnsondev.doboshacademyapp.model.data.loadMovies
import com.johnsondev.doboshacademyapp.model.entities.Movie

object MovieRepository {

    var moviesList: List<Movie> = listOf()

    suspend fun loadMoviesToRepository(context: Context){
        moviesList = loadMovies(context)
    }

}