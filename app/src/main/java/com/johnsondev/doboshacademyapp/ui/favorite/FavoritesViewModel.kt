package com.johnsondev.doboshacademyapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.data.repositories.ActorsRepository
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.base.BaseViewModel
import com.johnsondev.doboshacademyapp.utilities.states.Loading
import com.johnsondev.doboshacademyapp.utilities.states.LoadingState
import com.johnsondev.doboshacademyapp.utilities.states.Ready
import kotlinx.coroutines.launch

class FavoritesViewModel(app: Application) : BaseViewModel(app) {

    private var _favoriteMovies = MutableLiveData<List<Movie>>()
    private var _favoriteActors = MutableLiveData<List<Actor>>()
    private var _moviesLoadingState = MutableLiveData<LoadingState>()
    val moviesLoadingState: LiveData<LoadingState> get() = _moviesLoadingState
    private var _actorsLoadingState = MutableLiveData<LoadingState>()
    val actorsLoadingState: LiveData<LoadingState> get() = _actorsLoadingState

    fun loadFavoriteMoviesFromDb() {
        viewModelScope.launch(exceptionHandler()) {
            _moviesLoadingState.value = Loading
            MoviesRepository.loadFavoritesMoviesFromDb()
            mutableError.value = null
        }
    }

    fun getFavoriteMovies(): LiveData<List<Movie>> {
        _favoriteMovies = MoviesRepository.getFavoritesMovies()
        _moviesLoadingState.value = Ready
        return _favoriteMovies
    }

    fun loadFavoriteActorsFromDb(){
        viewModelScope.launch(exceptionHandler()) {
            _actorsLoadingState.value = Loading
            ActorsRepository.loadFavoritesActorsFromDb()
            mutableError.value = null
        }
    }

    fun getFavoriteActors(): LiveData<List<Actor>>{
        _favoriteActors = ActorsRepository.getFavoritesActors()
        _actorsLoadingState.value = Ready
        return _favoriteActors

    }

}