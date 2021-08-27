package com.johnsondev.doboshacademyapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.network.dto.ActorDetailsDto
import com.johnsondev.doboshacademyapp.data.repositories.ActorsRepository
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.InternetConnectionManager
import kotlinx.coroutines.launch

class MoviesListViewModel(application: Application) : AndroidViewModel(application) {

    private var popularMovies = MutableLiveData<List<Movie>>()
    val popularMoviesList: LiveData<List<Movie>> get() = popularMovies

    private var topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMoviesList: LiveData<List<Movie>> get() = topRatedMovies

    private var upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMoviesList: LiveData<List<Movie>> get() = upcomingMovies

    private var checkInternetConnection: InternetConnectionManager? = null


    fun getPopularMovies(): LiveData<List<Movie>> {
        if (popularMovies.value.isNullOrEmpty()) {
            viewModelScope.launch {
                popularMovies.postValue(MoviesRepository.getPopularMovies())
            }
        }
        return popularMovies
    }

    fun getTopRatedMovies(): LiveData<List<Movie>> {
        if (topRatedMovies.value.isNullOrEmpty()) {
            viewModelScope.launch {
                topRatedMovies.postValue(MoviesRepository.getTopRatedMovies())
            }
        }
        return  topRatedMovies
    }

    fun getUpcomingMovies(): LiveData<List<Movie>> {
        if (upcomingMovies.value.isNullOrEmpty()) {
            viewModelScope.launch {
                upcomingMovies.postValue(MoviesRepository.getUpcomingMovies())
            }
        }
        return upcomingMovies
    }

    fun isInternetConnectionAvailable(): Boolean {
        checkInternetConnection = InternetConnectionManager(getApplication())
        if (checkInternetConnection!!.isNetworkAvailable()) {
            return true
        }
        return false
    }

    suspend fun loadMoviesFromNet() {
        viewModelScope.launch {
            MoviesRepository.loadMoviesFromNet()
            popularMovies.postValue(MoviesRepository.getPopularMovies())
            topRatedMovies.postValue(MoviesRepository.getTopRatedMovies())
            upcomingMovies.postValue(MoviesRepository.getUpcomingMovies())
        }.join()
    }

}