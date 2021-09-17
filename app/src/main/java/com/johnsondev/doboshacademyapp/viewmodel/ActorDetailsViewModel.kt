package com.johnsondev.doboshacademyapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.network.dto.ActorDetailsDto
import com.johnsondev.doboshacademyapp.data.network.dto.ActorImageProfileDto
import com.johnsondev.doboshacademyapp.data.repositories.ActorsRepository
import com.johnsondev.doboshacademyapp.utilities.InternetConnectionManager
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.base.BaseViewModel
import kotlinx.coroutines.launch

class ActorDetailsViewModel(application: Application): BaseViewModel(application) {

    private var _actorDetails = MutableLiveData<ActorDetailsDto>()
    private var _actorMovieCredits = MutableLiveData<List<Movie>>()
    private var _actorImages = MutableLiveData<List<ActorImageProfileDto>>()

    fun loadActorDetailsById(id: Int) {
        viewModelScope.launch(exceptionHandler()) {
            ActorsRepository.loadActorDetailsById(id)
            mutableError.value = null
        }
    }
    fun getActorDetails(): LiveData<ActorDetailsDto> {
        _actorDetails = ActorsRepository.getActorDetails()
        return _actorDetails
    }

    fun getActorMovieCredits(): LiveData<List<Movie>> {
        _actorMovieCredits = ActorsRepository.getActorMovieCredits()
        return _actorMovieCredits
    }

    fun getActorImages(): LiveData<List<ActorImageProfileDto>> {
        _actorImages = ActorsRepository.getActorImages()
        return _actorImages
    }


    fun clearActorDetails() {
        _actorImages.value = emptyList()
        _actorMovieCredits.value = emptyList()
        _actorDetails.value = ActorDetailsDto()
    }

    fun checkInternetConnection(context: Context): Boolean {
        val internetConnectionManager = InternetConnectionManager(context)
        return internetConnectionManager.isNetworkAvailable()
    }

}