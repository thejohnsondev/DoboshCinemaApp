package com.johnsondev.doboshacademyapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.model.MovieRepository
import com.johnsondev.doboshacademyapp.model.entities.Movie

class MovieViewModel(application: Application): AndroidViewModel(application) {

    private var movieList: LiveData<List<Movie>>? = null

    fun getAllMovies(): LiveData<List<Movie>>{
        if(movieList?.value.isNullOrEmpty()){
            movieList = MovieRepository.getAllMovies()
        }
        return movieList!!
    }

}