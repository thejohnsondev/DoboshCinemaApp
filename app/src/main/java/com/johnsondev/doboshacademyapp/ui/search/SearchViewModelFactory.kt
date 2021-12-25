package com.johnsondev.doboshacademyapp.ui.search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnsondev.doboshacademyapp.data.repositories.movies.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.Constants.SUPPRESS_UNCHECKED_CAST

@Suppress(SUPPRESS_UNCHECKED_CAST)
class SearchViewModelFactory(
    val application: Application,
    private val moviesRepository: MoviesRepository
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(application, moviesRepository) as T
    }
}