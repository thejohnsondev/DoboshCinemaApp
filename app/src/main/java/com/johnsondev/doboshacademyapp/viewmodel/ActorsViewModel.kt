package com.johnsondev.doboshacademyapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnsondev.doboshacademyapp.data.repositories.ActorsRepository
import com.johnsondev.doboshacademyapp.data.models.Actor
import kotlinx.coroutines.launch

class ActorsViewModel : ViewModel() {

    private var mutableActorList = MutableLiveData<List<Actor>>()

    fun getActorsForCurrentMovie(): MutableLiveData<List<Actor>> {
        viewModelScope.launch {
            try {
                mutableActorList = ActorsRepository.getActorsForCurrentMovie()
            } catch (e: Exception) {
                Log.e("TAG", e.stackTrace.toString())
            }
        }
        return mutableActorList
    }

}