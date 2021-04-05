package com.johnsondev.doboshacademyapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.johnsondev.doboshacademyapp.model.MoviesRepository
import com.johnsondev.doboshacademyapp.model.data.Movie

class MovieViewModel(application: Application): AndroidViewModel(application) {

    private var topRatedMovies: LiveData<List<Movie>>? = null
    private var popularMovies: LiveData<List<Movie>>? = null
    private var upcomingMovies: LiveData<List<Movie>>? = null

    fun getTopRatedMovies(): LiveData<List<Movie>>{
        if(topRatedMovies?.value.isNullOrEmpty()){
            topRatedMovies = MoviesRepository.getTopRatedMovies()
        }
        return topRatedMovies!!
    }
    
    fun getPopularMovies(): LiveData<List<Movie>>{
        if (popularMovies?.value.isNullOrEmpty()){
            popularMovies = MoviesRepository.getLatestMovies()
        }
        return popularMovies!!
    }


    fun getUpcomingMovies(): LiveData<List<Movie>>{
        if (upcomingMovies?.value.isNullOrEmpty()){
            upcomingMovies = MoviesRepository.getUpcomingMovies()
        }
        return upcomingMovies!!
    }

    // TODO: 05.04.2021 Create switch to latest movies in FragmentMovieList 
    // TODO: 05.04.2021 handle exception 

}