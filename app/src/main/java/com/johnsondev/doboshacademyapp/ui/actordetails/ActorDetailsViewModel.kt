package com.johnsondev.doboshacademyapp.ui.actordetails

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.data.network.dto.ActorDetailsDto
import com.johnsondev.doboshacademyapp.data.network.dto.ActorImageProfileDto
import com.johnsondev.doboshacademyapp.data.repositories.actors.ActorsRepository
import com.johnsondev.doboshacademyapp.utilities.InternetConnectionManager
import com.johnsondev.doboshacademyapp.utilities.base.BaseViewModel
import kotlinx.coroutines.launch

class ActorDetailsViewModel(
    application: Application,
    private val actorsRepository: ActorsRepository
) : BaseViewModel(application) {

    private var _actorDetails = MutableLiveData<ActorDetailsDto>()
    private var _actorMovieCredits = MutableLiveData<List<Movie>>()
    private var _actorImages = MutableLiveData<List<ActorImageProfileDto>>()

    private var _favoriteActorsIds = MutableLiveData<List<Int>>()


    fun loadFavoriteActorsIds() {
        viewModelScope.launch(exceptionHandler()) {
            _favoriteActorsIds = actorsRepository.getFavoriteActorsIds()
            mutableError.value = null
        }
    }

    fun isActorFavorite(actorId: Int): Boolean {
        return _favoriteActorsIds.value?.contains(actorId) == true
    }

    fun insertActorToFavorites(actorId: Int) {
        viewModelScope.launch(exceptionHandler()) {
            actorsRepository.insertActorToFavorites(actorId)
            mutableError.value = null
        }
    }

    fun deleteActorFromFavorites(actorId: Int) {
        viewModelScope.launch(exceptionHandler()) {
            actorsRepository.deleteActorFromFavorites(actorId)
            mutableError.value = null
        }
    }

    fun loadActorDetailsById(id: Int) {
        viewModelScope.launch(exceptionHandler()) {
            actorsRepository.loadActorDetailsById(id)
            mutableError.value = null
        }
    }

    fun getActorDetails(): LiveData<ActorDetailsDto> {
        _actorDetails = actorsRepository.getActorDetails()
        return _actorDetails
    }

    fun getActorMovieCredits(): LiveData<List<Movie>> {
        _actorMovieCredits = actorsRepository.getActorMovieCredits()
        return _actorMovieCredits
    }

    fun getActorImages(): LiveData<List<ActorImageProfileDto>> {
        _actorImages = actorsRepository.getActorImages()
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