package com.johnsondev.doboshacademyapp.ui.movielist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnsondev.doboshacademyapp.data.repositories.movies.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.Constants.SUPPRESS_UNCHECKED_CAST

@Suppress(SUPPRESS_UNCHECKED_CAST)
class MoviesListViewModelFactory(
    val application: Application,
    private val moviesRepository: MoviesRepository
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesListViewModel(application, moviesRepository) as T
    }
}