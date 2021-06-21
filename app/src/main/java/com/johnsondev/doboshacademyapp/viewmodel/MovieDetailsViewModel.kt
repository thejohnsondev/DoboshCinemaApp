package com.johnsondev.doboshacademyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieDetailsViewModel : ViewModel() {

    fun getMovieById(id: Int): Movie {
        return MoviesRepository.getMovieByIdFromDb(id)
    }

}