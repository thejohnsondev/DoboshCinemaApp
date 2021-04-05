package com.johnsondev.doboshacademyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnsondev.doboshacademyapp.model.MoviesRepository
import com.johnsondev.doboshacademyapp.model.data.Movie
import kotlinx.coroutines.launch

class MovieDetailViewModel: ViewModel() {

    private lateinit var movieById: LiveData<Movie>

    fun getMovieById(id: Int): LiveData<Movie>{
            viewModelScope.launch {
                movieById = MoviesRepository.loadMovieById(id)
            }
        return movieById
    }

    // TODO: 05.04.2021 delete movieDetailViewModel, and pass current movie to FragmentMovieDetails with bundle from fragmentMovieList 

}