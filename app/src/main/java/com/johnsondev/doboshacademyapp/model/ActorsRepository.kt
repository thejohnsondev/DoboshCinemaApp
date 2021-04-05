package com.johnsondev.doboshacademyapp.model

import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.model.data.Actor
import com.johnsondev.doboshacademyapp.model.dto.ActorDto
import com.johnsondev.doboshacademyapp.network.NetworkService
import com.johnsondev.doboshacademyapp.tools.DtoMapper

object ActorsRepository {

    private val movieApi = NetworkService.MOVIE_API

    private var actors = MutableLiveData<List<Actor>>()
    private var actorsList: List<ActorDto> = listOf()

    suspend fun loadActors(movieId: Int): MutableLiveData<List<Actor>> {
        actorsList = movieApi.getActors(movieId).cast
        actors.postValue(actorsList.distinct().map {
            DtoMapper.convertActorFromDto(it)
        })
        return actors
    }

    fun getActorsForCurrentMovie(): MutableLiveData<List<Actor>> {
        return actors
    }

}