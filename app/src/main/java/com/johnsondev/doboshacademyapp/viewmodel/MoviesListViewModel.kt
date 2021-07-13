package com.johnsondev.doboshacademyapp.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.utilities.InternetConnectionManager
import com.johnsondev.doboshacademyapp.utilities.getUpdateTime
import kotlinx.coroutines.launch

class MoviesListViewModel(application: Application) : AndroidViewModel(application) {

    private var popularMovies = MutableLiveData<List<Movie>>()
    val popularMoviesList: LiveData<List<Movie>> get() = popularMovies

    private var topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMoviesList: LiveData<List<Movie>> get() = topRatedMovies

    private var upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMoviesList: LiveData<List<Movie>> get() = upcomingMovies

    private var lastUpdateTime = MutableLiveData<String>()

    private var checkInternetConnection: InternetConnectionManager? = null

    private var movieList = MutableLiveData<List<Movie>>()

    fun getLastUpdateTime(context: Context): LiveData<String> {
        if (lastUpdateTime.value.isNullOrEmpty()) {
            viewModelScope.launch {
                lastUpdateTime.postValue(getUpdateTime(context))
            }
        }
        return lastUpdateTime
    }

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