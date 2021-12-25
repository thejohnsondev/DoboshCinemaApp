package com.johnsondev.doboshacademyapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.data.repositories.actors.ActorsRepository
import com.johnsondev.doboshacademyapp.data.repositories.movies.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.base.BaseViewModel
import com.johnsondev.doboshacademyapp.utilities.states.Loading
import com.johnsondev.doboshacademyapp.utilities.states.LoadingState
import com.johnsondev.doboshacademyapp.utilities.states.Ready
import kotlinx.coroutines.launch

class FavoritesViewModel(
    val app: Application,
    private val actorsRepository: ActorsRepository,
    private val moviesRepository: MoviesRepository
) : BaseViewModel(app) {

    private var _favoriteMovies = MutableLiveData<List<Movie>>()
    private var _favoriteActors = MutableLiveData<List<Actor>>()
    private var _moviesLoadingState = MutableLiveData<LoadingState>()
    val moviesLoadingState: LiveData<LoadingState> get() = _moviesLoadingState
    private var _actorsLoadingState = MutableLiveData<LoadingState>()
    val actorsLoadingState: LiveData<LoadingState> get() = _actorsLoadingState

    fun loadFavoriteMoviesFromDb() {
        viewModelScope.launch(exceptionHandler()) {
            _moviesLoadingState.value = Loading
            moviesRepository.loadFavoritesMoviesFromDb()
            mutableError.value = null
        }
    }

    fun getFavoriteMovies(): LiveData<List<Movie>> {
        _favoriteMovies = moviesRepository.getFavoritesMovies()
        _moviesLoadingState.value = Ready
        return _favoriteMovies
    }

    fun loadFavoriteActorsFromDb() {
        viewModelScope.launch(exceptionHandler()) {
            _actorsLoadingState.value = Loading
            actorsRepository.loadFavoritesActorsFromDb()
            mutableError.value = null
        }
    }

    fun getFavoriteActors(): LiveData<List<Actor>> {
        _favoriteActors = actorsRepository.getFavoritesActors()
        _actorsLoadingState.value = Ready
        return _favoriteActors

    }

}