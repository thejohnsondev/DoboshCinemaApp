package com.johnsondev.doboshacademyapp.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.model.data.loadMovies
import com.johnsondev.doboshacademyapp.model.entities.Movie

object MovieRepository {

    private var moviesList = MutableLiveData<List<Movie>>()
    private var movies: List<Movie> = listOf()


    suspend fun loadMoviesToRepository(context: Context){
        movies = loadMovies(context)
        moviesList.postValue(movies)
    }

    fun getAllMovies(): LiveData<List<Movie>>{
        return moviesList
    }

}