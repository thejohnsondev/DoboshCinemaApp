package com.johnsondev.doboshacademyapp.data.repositories

import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.App
import com.johnsondev.doboshacademyapp.data.db.entities.FavoriteEntity
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.data.models.base.CrewMember
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.data.network.NetworkService
import com.johnsondev.doboshacademyapp.data.network.dto.ActorDetailsDto
import com.johnsondev.doboshacademyapp.data.network.dto.ActorImageProfileDto
import com.johnsondev.doboshacademyapp.data.network.exception.ConnectionErrorException
import com.johnsondev.doboshacademyapp.data.network.exception.UnexpectedErrorException
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.FAVORITE_ACTOR_ENTITY_TYPE
import com.johnsondev.doboshacademyapp.utilities.DtoMapper
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

object ActorsRepository {

    private val movieApi = NetworkService.MOVIE_API
    private val favoritesDatabase = App.getInstance().getFavoritesDatabase()

    private var actors = MutableLiveData<List<Actor>>()
    private var crew = MutableLiveData<List<CrewMember>>()

    private var actorDetails = MutableLiveData<ActorDetailsDto>()
    private var actorMovieCredits = MutableLiveData<List<Movie>>()
    private var actorImages = MutableLiveData<List<ActorImageProfileDto>>()

    private var favoriteActors = MutableLiveData<List<Actor>>()
    private var favoriteActorsIds = MutableLiveData<List<Int>>()

    suspend fun getFavoriteActorsIds(): MutableLiveData<List<Int>> {
        try {
            favoriteActorsIds.value = favoritesDatabase.favoritesDao()
                .getFavoritesEntityIdByType(FAVORITE_ACTOR_ENTITY_TYPE)
        } catch (e: Exception) {
            handleExceptions(e)
        }
        return favoriteActorsIds
    }


    suspend fun loadFavoritesActorsFromDb() {
        try {
            favoriteActors.value = favoritesDatabase.favoritesDao()
                .getFavoritesEntityIdByType(FAVORITE_ACTOR_ENTITY_TYPE).map { id ->
                    DtoMapper.convertActorFromDto(movieApi.getActor(id))
                }

        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    suspend fun insertActorToFavorites(actorId: Int) {

        try {
            favoritesDatabase.favoritesDao().insertFavoriteEntity(
                FavoriteEntity(
                    entityType = FAVORITE_ACTOR_ENTITY_TYPE,
                    entityId = actorId
                )
            )
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    suspend fun deleteActorFromFavorites(actorId: Int) {
        try {
            favoritesDatabase.favoritesDao()
                .deleteFavoriteEntity(FAVORITE_ACTOR_ENTITY_TYPE, actorId)
        } catch (e: Exception) {
            handleExceptions(e)
        }

    }

    fun getFavoritesActors(): MutableLiveData<List<Actor>> = favoriteActors

    suspend fun loadCast(movieId: Int) {
        try {
            val response = movieApi.getActors(movieId)

            actors.postValue(response.cast.distinct().map {
                DtoMapper.convertActorFromDto(it)
            })

            crew.postValue(response.crew.distinct().map {
                DtoMapper.convertCrewMemberFromDto(it)
            })

        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    fun getActorsForCurrentMovie(): MutableLiveData<List<Actor>> = actors

    fun getCrewForCurrentMovie(): MutableLiveData<List<CrewMember>> = crew

    suspend fun loadActorDetailsById(id: Int) {
        try {
            actorDetails.postValue(movieApi.getActorDetails(id))
            actorMovieCredits.postValue(movieApi.getActorMovieCredits(id).cast.distinct().map {
                DtoMapper.convertMovieFromDto(it)
            })
            actorImages.postValue(movieApi.getActorImages(id).profiles)
        } catch (e: Exception) {
            handleExceptions(e)
        }


    }

    fun getActorDetails(): MutableLiveData<ActorDetailsDto> = actorDetails

    fun getActorMovieCredits(): MutableLiveData<List<Movie>> = actorMovieCredits

    fun getActorImages(): MutableLiveData<List<ActorImageProfileDto>> = actorImages

    private fun handleExceptions(e: Exception) {
        throw when (e) {
            is IOException, is HttpException, is TimeoutException -> ConnectionErrorException()
            else -> UnexpectedErrorException()
        }
    }
}