package com.johnsondev.doboshacademyapp.utilities.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val mutableError = MutableLiveData<String>()

    val error: LiveData<String> get() = mutableError

    fun exceptionHandler() = CoroutineExceptionHandler { _, throwable ->
        mutableError.value = throwable.message
    }
}