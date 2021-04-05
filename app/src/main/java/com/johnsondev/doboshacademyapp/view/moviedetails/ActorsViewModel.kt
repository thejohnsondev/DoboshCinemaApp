package com.johnsondev.doboshacademyapp.view.moviedetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnsondev.doboshacademyapp.model.ActorsRepository
import com.johnsondev.doboshacademyapp.model.data.Actor
import kotlinx.coroutines.launch

class ActorsViewModel : ViewModel() {

    private var mutableActorList = MutableLiveData<List<Actor>>()

    fun getActorsForCurrentMovie(): MutableLiveData<List<Actor>> {
        viewModelScope.launch {
            try {
                mutableActorList = ActorsRepository.getActorsForCurrentMovie()
            } catch (e: Exception) {
                Log.d("TAG", "error " + e.stackTrace)
            }
        }
        return mutableActorList
    }

}