package com.johnsondev.doboshacademyapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.base.BaseViewModel
import kotlinx.coroutines.launch

class FavoritesViewModel(app: Application) : BaseViewModel(app) {

    private var _favoriteMovies = MutableLiveData<List<Movie>>()
    private var _favoriteActors = MutableLiveData<List<Actor>>()


    fun loadFavoriteMoviesFromDb() {
        viewModelScope.launch(exceptionHandler()) {
            MoviesRepository.loadFavoritesMoviesFromDb()
        }
    }

    fun getFavoriteMovies(): LiveData<List<Movie>> {
        _favoriteMovies = MoviesRepository.getFavoritesMovies()
        return _favoriteMovies
    }

    fun loadFavoriteActorsFromDb(){
        viewModelScope.launch(exceptionHandler()) {
            MoviesRepository.loadFavoritesActorsFromDb()
        }
    }

    fun getFavoriteActors(): LiveData<List<Actor>>{

        _favoriteActors = MoviesRepository.getFavoritesActors()
        return _favoriteActors

    }

}