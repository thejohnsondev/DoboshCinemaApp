package com.johnsondev.doboshacademyapp.data.repositories

import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.network.NetworkService
import com.johnsondev.doboshacademyapp.data.network.dto.ActorDto
import com.johnsondev.doboshacademyapp.utilities.DtoMapper

object ActorsRepository {

    private val movieApi = NetworkService.MOVIE_API

    private var actors = MutableLiveData<List<Actor>>()
    private var actorsList: List<ActorDto> = listOf()

    suspend fun loadActors(movieId: Int) {
        actorsList = movieApi.getActors(movieId).cast
        actors.postValue(actorsList.distinct().map {
            DtoMapper.convertActorFromDto(it)
        })
    }

    fun getActorsForCurrentMovie(): MutableLiveData<List<Actor>> {
        return actors
    }

}