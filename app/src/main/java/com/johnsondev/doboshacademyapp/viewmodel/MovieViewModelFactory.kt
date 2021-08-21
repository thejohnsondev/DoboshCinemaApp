package com.johnsondev.doboshacademyapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnsondev.doboshacademyapp.R

class MovieViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MoviesListViewModel::class.java)){
            return MoviesListViewModel(application) as T
        }
        throw IllegalArgumentException(application.getString(R.string.uncknown_viewvmodel))
    }
}