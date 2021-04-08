package com.johnsondev.doboshacademyapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.data.models.Movie

class MovieViewModel(application: Application): AndroidViewModel(application) {

    private var topRatedMovies: LiveData<List<Movie>>? = null
    private var popularMovies: LiveData<List<Movie>>? = null
    private var upcomingMovies: LiveData<List<Movie>>? = null

    private var movieList = MutableLiveData<List<Movie>>()

    fun getPopularMovies(): LiveData<List<Movie>>{
        if (popularMovies?.value.isNullOrEmpty()){
            popularMovies = MoviesRepository.getLatestMovies()
        }
        return popularMovies!!
    }

    private fun getTopRatedMovies(): LiveData<List<Movie>>{
        if(topRatedMovies?.value.isNullOrEmpty()){
            topRatedMovies = MoviesRepository.getTopRatedMovies()
        }
        return topRatedMovies!!
    }
    

    private fun getUpcomingMovies(): LiveData<List<Movie>>{
        if (upcomingMovies?.value.isNullOrEmpty()){
            upcomingMovies = MoviesRepository.getUpcomingMovies()
        }
        return upcomingMovies!!
    }

    fun getAnotherMovieList(): LiveData<List<Movie>>{
        return movieList
    }

    fun changeMoviesList(checkedBtnId: Int){
        when(checkedBtnId){
            R.id.btn_popular -> movieList.value = getPopularMovies().value
            R.id.btn_top_rated -> movieList.value = getTopRatedMovies().value
            R.id.btn_upcoming -> movieList.value = getUpcomingMovies().value
        }
    }

    // TODO: 05.04.2021 handle exception 
    // TODO: 06.04.2021 refactor packages 

}