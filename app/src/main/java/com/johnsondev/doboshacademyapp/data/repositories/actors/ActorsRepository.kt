package com.johnsondev.doboshacademyapp.data.repositories.actors

import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.data.models.base.CrewMember
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.data.network.dto.ActorDetailsDto
import com.johnsondev.doboshacademyapp.data.network.dto.ActorImageProfileDto

interface ActorsRepository {
    suspend fun getFavoriteActorsIds(): MutableLiveData<List<Int>>
    suspend fun loadFavoritesActorsFromDb()
    suspend fun insertActorToFavorites(actorId: Int)
    suspend fun deleteActorFromFavorites(actorId: Int)
    fun getFavoritesActors(): MutableLiveData<List<Actor>>
    fun getActorsForCurrentMovie(): MutableLiveData<List<Actor>>
    fun getCrewForCurrentMovie(): MutableLiveData<List<CrewMember>>
    fun getActorDetails(): MutableLiveData<ActorDetailsDto>
    fun getActorMovieCredits(): MutableLiveData<List<Movie>>
    fun getActorImages(): MutableLiveData<List<ActorImageProfileDto>>
    suspend fun loadCast(movieId: Int)
    suspend fun loadActorDetailsById(id: Int)
}