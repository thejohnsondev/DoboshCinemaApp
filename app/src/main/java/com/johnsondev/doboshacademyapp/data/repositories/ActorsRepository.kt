package com.johnsondev.doboshacademyapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.network.NetworkService
import com.johnsondev.doboshacademyapp.data.network.dto.ActorDetailsDto
import com.johnsondev.doboshacademyapp.data.network.dto.ActorDto
import com.johnsondev.doboshacademyapp.data.network.dto.ActorImageProfileDto
import com.johnsondev.doboshacademyapp.data.network.exception.ConnectionErrorException
import com.johnsondev.doboshacademyapp.data.network.exception.UnexpectedErrorException
import com.johnsondev.doboshacademyapp.utilities.DtoMapper
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

object ActorsRepository {

    private val movieApi = NetworkService.MOVIE_API

    private var actors = MutableLiveData<List<Actor>>()

    private var actorDetails = MutableLiveData<ActorDetailsDto>()
    private var actorMovieCredits = MutableLiveData<List<Movie>>()
    private var actorImages = MutableLiveData<List<ActorImageProfileDto>>()

    suspend fun loadActors(movieId: Int) {
        try {
            actors.postValue(movieApi.getActors(movieId).cast.distinct().map {
                DtoMapper.convertActorFromDto(it)
            })
        }catch (e: Exception){
            handleExceptions(e)
        }

    }

    fun getActorsForCurrentMovie(): MutableLiveData<List<Actor>> {
        return actors
    }

    suspend fun loadActorDetailsById(id: Int) {
        try {
            actorDetails.postValue(movieApi.getActorDetails(id))
            actorMovieCredits.postValue(movieApi.getActorMovieCredits(id).cast.distinct().map {
                DtoMapper.convertMovieFromDto(it)
            })
            actorImages.postValue(movieApi.getActorImages(id).profiles)
        }catch (e: Exception){
            handleExceptions(e)
        }


    }

    fun getActorDetails(): MutableLiveData<ActorDetailsDto> = actorDetails

    fun getActorMovieCredits(): MutableLiveData<List<Movie>> = actorMovieCredits

    fun getActorImages(): MutableLiveData<List<ActorImageProfileDto>> = actorImages

    private fun handleExceptions(e: Exception) {
        throw when(e){
            is IOException, is HttpException, is TimeoutException -> ConnectionErrorException()
            else -> UnexpectedErrorException()
        }
    }
}