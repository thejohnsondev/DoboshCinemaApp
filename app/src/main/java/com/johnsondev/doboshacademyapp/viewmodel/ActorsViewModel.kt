package com.johnsondev.doboshacademyapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnsondev.doboshacademyapp.data.repositories.ActorsRepository
import com.johnsondev.doboshacademyapp.data.models.Actor
import kotlinx.coroutines.launch

class ActorsViewModel : ViewModel() {

    private var _mutableActorList = MutableLiveData<List<Actor>>()
    val mutableActorList: LiveData<List<Actor>> get() =_mutableActorList

    fun getActorsForCurrentMovie(){
        viewModelScope.launch {
            try {
                _mutableActorList = ActorsRepository.getActorsForCurrentMovie()
            } catch (e: Exception) {
                Log.e("TAG", e.stackTrace.toString())
            }
        }
    }

    fun clearActorList(){
        _mutableActorList.value = listOf()
        Log.d("TAG", "clearList")
    }

}