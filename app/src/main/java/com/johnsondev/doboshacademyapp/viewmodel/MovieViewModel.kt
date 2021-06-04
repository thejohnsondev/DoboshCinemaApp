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

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private var popularMovies = MutableLiveData<List<Movie>>()
    val popularMoviesList: LiveData<List<Movie>> get() = popularMovies

    private var topRatedMovies = MutableLiveData<List<Movie>>()
    private val topRatedMoviesList: LiveData<List<Movie>> get() = topRatedMovies

    private var upcomingMovies = MutableLiveData<List<Movie>>()
    private val upcomingMoviesList: LiveData<List<Movie>> get() = upcomingMovies

    private var lastUpdateTime = MutableLiveData<String>()

    private var checkInternetConnection: InternetConnectionManager? = null

    private var movieList = MutableLiveData<List<Movie>>()

    fun getLastUpdateTime(context: Context): LiveData<String> {
        if (lastUpdateTime.value.isNullOrEmpty()){
            viewModelScope.launch {
                lastUpdateTime.postValue(getUpdateTime(context))
            }
        }
        return lastUpdateTime
    }

    fun getPopularMovies() {
        if (popularMovies.value.isNullOrEmpty()) {
            viewModelScope.launch {
                popularMovies.postValue(MoviesRepository.getPopularMovies())
            }
        }
    }

    fun getTopRatedMovies() {
        if (topRatedMovies.value.isNullOrEmpty()) {
            viewModelScope.launch {
                topRatedMovies.postValue(MoviesRepository.getTopRatedMovies())
            }
        }
    }

    fun getUpcomingMovies() {
        if (upcomingMovies.value.isNullOrEmpty()) {
            viewModelScope.launch {
                upcomingMovies.postValue(MoviesRepository.getUpcomingMovies())
            }
        }
    }

    fun getAnotherMovieList(): LiveData<List<Movie>> {
        return movieList
    }

    fun changeMoviesList(checkedBtnId: Int) {
        viewModelScope.launch {
            when (checkedBtnId) {
                R.id.btn_popular -> movieList.value = popularMoviesList.value
                R.id.btn_top_rated -> movieList.value = topRatedMoviesList.value
                R.id.btn_upcoming -> movieList.value = upcomingMoviesList.value
            }
        }
    }

    fun isInternetConnectionAvailable(): Boolean {
        checkInternetConnection = InternetConnectionManager(getApplication())
        if (checkInternetConnection!!.isNetworkAvailable()) {
            return true
        }
        return false
    }


    suspend fun loadMoviesFromNet(){
        viewModelScope.launch {
            MoviesRepository.loadMoviesFromNet()
            popularMovies.postValue(MoviesRepository.getPopularMovies())
            topRatedMovies.postValue(MoviesRepository.getTopRatedMovies())
            upcomingMovies.postValue(MoviesRepository.getUpcomingMovies())
        }.join()
        // TODO: 04.06.2021 change repository to return livedata with movies
    }

}