package com.johnsondev.doboshacademyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.repositories.ActorsRepository
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieDetailsViewModel : ViewModel() {

    private var _mutableActorList = MutableLiveData<List<Actor>>()

    fun getActorsForCurrentMovie(): LiveData<List<Actor>> {
        viewModelScope.launch {
            try {
                _mutableActorList = ActorsRepository.getActorsForCurrentMovie()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return _mutableActorList
    }

    fun loadActorsForMovieById(movieId: Int){
        viewModelScope.launch {
            ActorsRepository.loadActors(movieId)
        }
    }

    fun clearActorList() {
        _mutableActorList.value = listOf()
    }

    fun getMovieById(id: Int): Movie {
        return MoviesRepository.getMovieByIdFromDb(id)
    }

}