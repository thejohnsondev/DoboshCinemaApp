package com.johnsondev.doboshacademyapp.utilities.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {

    protected val mutableError = MutableLiveData<String>()

    val error: LiveData<String> get() = mutableError

    fun exceptionHandler() = CoroutineExceptionHandler { _, throwable ->
        mutableError.value = throwable.message
    }
}