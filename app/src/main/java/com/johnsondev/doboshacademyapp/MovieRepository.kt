package com.johnsondev.doboshacademyapp

import android.content.Context
import com.johnsondev.doboshacademyapp.data.loadMovies
import com.johnsondev.doboshacademyapp.model.Movie

object MovieRepository {

    var moviesList: List<Movie> = listOf()

    suspend fun loadMoviesToRepository(context: Context){
        moviesList = loadMovies(context)
    }

}