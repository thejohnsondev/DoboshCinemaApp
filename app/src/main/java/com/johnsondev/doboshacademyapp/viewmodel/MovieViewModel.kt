package com.johnsondev.doboshacademyapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.utilities.InternetConnectionManager
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private var topRatedMovies: LiveData<List<Movie>>? = null

    private var popularMovies = MutableLiveData<List<Movie>>()
    val popularMoviesList: LiveData<List<Movie>> get() = popularMovies

    private var upcomingMovies: LiveData<List<Movie>>? = null

    private var checkInternetConnection: InternetConnectionManager? = null

    private var movieList = MutableLiveData<List<Movie>>()

    fun getPopularMovies() {
        if (popularMovies.value.isNullOrEmpty()) {
            viewModelScope.launch {
                popularMovies.postValue(MoviesRepository.getPopularMovies())
            }
        }
    }

    private fun getTopRatedMovies(): LiveData<List<Movie>> {
        if (topRatedMovies?.value.isNullOrEmpty()) {
            topRatedMovies = MoviesRepository.getTopRatedMovies()
        }
        return topRatedMovies!!
    }


    private fun getUpcomingMovies(): LiveData<List<Movie>> {
        if (upcomingMovies?.value.isNullOrEmpty()) {
            upcomingMovies = MoviesRepository.getUpcomingMovies()
        }
        return upcomingMovies!!
    }

    fun getAnotherMovieList(): LiveData<List<Movie>> {
        return movieList
    }

    fun changeMoviesList(checkedBtnId: Int) {
        when (checkedBtnId) {
            R.id.btn_popular -> movieList.value = popularMoviesList.value
            R.id.btn_top_rated -> movieList.value = getTopRatedMovies().value
            R.id.btn_upcoming -> movieList.value = getUpcomingMovies().value
        }
    }

    fun isInternetConnectionAvailable(): Boolean {
        checkInternetConnection = InternetConnectionManager(getApplication())
        if (checkInternetConnection!!.isNetworkAvailable()) {
            return true
        }
        return false
    }

    suspend fun loadMoviesFromNetwork() {
        viewModelScope.launch {
            val job1 = viewModelScope.launch {
                MoviesRepository.loadPopularMoviesFromNet()
            }
            val job2 = viewModelScope.launch {
                MoviesRepository.loadTopRatedMovies()
            }
            val job3 = viewModelScope.launch {
                MoviesRepository.loadUpcomingMovies()
            }
            job1.join()
            job2.join()
            job3.join()
        }.join()
    }

    suspend fun loadPopularMoviesFromNet(){
        viewModelScope.launch {
            MoviesRepository.loadPopularMoviesFromNet()
            popularMovies.postValue(MoviesRepository.getPopularMovies())
        }.join()
    }



}