package com.johnsondev.doboshacademyapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.base.BaseViewModel
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : BaseViewModel(application) {

    private var _moviesResultList = MutableLiveData<List<Movie>>()
    private var _actorsResultList = MutableLiveData<List<Actor>>()


    fun searchQuery(query: String) {
        viewModelScope.launch {
            try {
                MoviesRepository.searchQuery(query)
            } catch (e: Exception) {
                Log.d("TAG", e.message.toString())
            }

        }
    }

    fun getMoviesResultList(): LiveData<List<Movie>> {
        _moviesResultList = MoviesRepository.getMoviesSearchResult()
        return _moviesResultList
    }

    fun getActorsResultList(): LiveData<List<Actor>> {
        _actorsResultList = MoviesRepository.getActorsSearchResult()
        return _actorsResultList
    }

}