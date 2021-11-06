package com.johnsondev.doboshacademyapp.ui.movielist

import android.util.Log
import androidx.lifecycle.*
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.data.models.base.Genre
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.data.repositories.movies.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesListViewModel(
    private val moviesRepository: MoviesRepository
) : BaseViewModel() {

    init {
        Log.d("TAG","Create new ListViewModel")
    }

    private var popularMovies = MutableLiveData<List<Movie>>()
    val popularMoviesList: LiveData<List<Movie>> get() = popularMovies
    private var topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMoviesList: LiveData<List<Movie>> get() = topRatedMovies
    private var upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMoviesList: LiveData<List<Movie>> get() = upcomingMovies

    private var _popularGenres = MutableLiveData<List<Genre>>()
    private var _moviesByGenre = MutableLiveData<List<Movie>>()
    private var _popularActors = MutableLiveData<List<Actor>>()

    fun loadGenresList() {
        viewModelScope.launch(exceptionHandler()) {
            moviesRepository.loadGenresList()
            mutableError.value = null
        }
    }

    fun getGenresList(): LiveData<List<Genre>> {
        _popularGenres = moviesRepository.getGenresList()
        return _popularGenres
    }

    fun loadMoviesByGenreId(id: Int) {
        viewModelScope.launch(exceptionHandler()) {
            moviesRepository.loadMoviesByGenreId(id)
            mutableError.value = null
        }
    }

    fun getMoviesByGenre(): LiveData<List<Movie>> {
        _moviesByGenre = moviesRepository.getMoviesByGenre()
        return _moviesByGenre
    }

    fun loadPopularActors() {
        viewModelScope.launch(exceptionHandler()) {
            moviesRepository.loadPopularActorsList()
            mutableError.value = null
        }
    }

    fun getPopularActors(): LiveData<List<Actor>> {
        _popularActors = moviesRepository.getPopularActors()
        return _popularActors
    }


    fun getPopularMovies(): LiveData<List<Movie>> {
        if (popularMovies.value.isNullOrEmpty()) {
            viewModelScope.launch(exceptionHandler()) {
                popularMovies.postValue(moviesRepository.getPopularMovies())
                mutableError.value = null
            }
        }
        return popularMovies
    }

    fun getTopRatedMovies(): LiveData<List<Movie>> {
        if (topRatedMovies.value.isNullOrEmpty()) {
            viewModelScope.launch(exceptionHandler()) {
                topRatedMovies.postValue(moviesRepository.getTopRatedMovies())
                mutableError.value = null
            }
        }
        return topRatedMovies
    }

    fun getUpcomingMovies(): LiveData<List<Movie>> {
        if (upcomingMovies.value.isNullOrEmpty()) {
            viewModelScope.launch(exceptionHandler()) {
                upcomingMovies.postValue(moviesRepository.getUpcomingMovies())
                mutableError.value = null
            }
        }
        return upcomingMovies
    }

    suspend fun loadMoviesFromNet() {
        viewModelScope.launch(exceptionHandler()) {
            if (popularMovies.value.isNullOrEmpty()) {
                moviesRepository.loadPopularMoviesFromNet()
                moviesRepository.loadTopRatedMoviesFromNet()
                moviesRepository.loadUpcomingMoviesFromNet()
                popularMovies.postValue(moviesRepository.getPopularMovies())
                topRatedMovies.postValue(moviesRepository.getTopRatedMovies())
                upcomingMovies.postValue(moviesRepository.getUpcomingMovies())
                mutableError.value = null
            }
        }.join()
    }

    class Factory @Inject constructor(
        private val moviesRepository: MoviesRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MoviesListViewModel(moviesRepository) as T
        }

    }
}
