package com.johnsondev.doboshacademyapp.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnsondev.doboshacademyapp.data.repositories.movies.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.base.BaseViewModel
import javax.inject.Inject

class SettingsViewModel(
    private val moviesRepository: MoviesRepository
) : BaseViewModel() {

    init {
        Log.d("TAG", "Create new SettingsViewModel")
    }

    class Factory @Inject constructor(
        private val moviesRepository: MoviesRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SettingsViewModel(moviesRepository) as T
        }

    }

}