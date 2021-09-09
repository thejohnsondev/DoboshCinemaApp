package com.johnsondev.doboshacademyapp.data.repositories

import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Genre
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.models.MovieDetails
import com.johnsondev.doboshacademyapp.data.network.NetworkService
import com.johnsondev.doboshacademyapp.data.network.dto.MovieDetailsDto
import com.johnsondev.doboshacademyapp.data.network.dto.MovieDto
import com.johnsondev.doboshacademyapp.data.network.dto.MovieImageDto
import com.johnsondev.doboshacademyapp.data.network.dto.MovieVideoDto
import com.johnsondev.doboshacademyapp.data.network.exception.ConnectionErrorException
import com.johnsondev.doboshacademyapp.data.network.exception.UnexpectedErrorException
import com.johnsondev.doboshacademyapp.utilities.Constants.BACKDROP_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.LANG_RU
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_KEY
import com.johnsondev.doboshacademyapp.utilities.DtoMapper
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException
import kotlin.Exception

object MoviesRepository {

    private val movieApi = NetworkService.MOVIE_API

    private var popularMoviesList: List<Movie> = listOf()
    private var topRatedMoviesList: List<Movie> = listOf()
    private var upcomingMoviesList: List<Movie> = listOf()
    private var genresList = MutableLiveData<List<Genre>>()
    private var moviesByGenre = MutableLiveData<List<Movie>>()
    private var popularActorsList = MutableLiveData<List<Actor>>()
    private var actorImgAverageColorBody = MutableLiveData<Int>()
    private var actorImgAverageColorText = MutableLiveData<Int>()


    private var currentMovieDetails = MutableLiveData<MovieDetails>()
    private var movieRecommendations = MutableLiveData<List<Movie>>()
    private var similarMovies = MutableLiveData<List<Movie>>()
    private var movieVideos = MutableLiveData<List<MovieVideoDto>>()
    private var movieImages = MutableLiveData<Map<String, List<MovieImageDto>>>()


    suspend fun loadSimilarMoviesById(movieId: Int){
        try {
            similarMovies.postValue(movieApi.getSimilarMoviesByMovieId(movieId).results.distinct().map {
                DtoMapper.convertMovieFromDto(it)
            })
        }catch (e: Exception){
            handleExceptions(e)
        }
    }

    suspend fun loadRecommendationsByMovieId(id: Int) {
        try {
            movieRecommendations.postValue(
                movieApi.getRecommendationsByMovieId(id).results.distinct().map {
                    DtoMapper.convertMovieFromDto(it)
                })
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    suspend fun loadMovieImages(id: Int) {
        try {
            val movieImagesFromApi = movieApi.getMovieImages(id)
            movieImages.postValue(
                mapOf(
                    POSTER_KEY to movieImagesFromApi.posters,
                    BACKDROP_KEY to movieImagesFromApi.backdrops
                )
            )
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    suspend fun loadMovieVideosById(id: Int) {
        try {
            movieVideos.postValue(movieApi.getMovieVideos(id, LANG_RU).results)
        } catch (e: Exception) {
            handleExceptions(e)
        }

    }

    suspend fun loadMovieById(id: Int) {
        try {
            currentMovieDetails.value =
                DtoMapper.convertMovieDetailsFromDto(movieApi.getMovieById(id))
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    suspend fun loadGenresList() {
        try {
            genresList.value = movieApi.getGenresList().genres.distinct().map {
                DtoMapper.convertGenreFromDto(it)
            }
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    suspend fun loadMoviesByGenreId(id: Int) {
        try {
            moviesByGenre.value = movieApi.getMoviesListByGenreId(id).results.distinct().map {
                DtoMapper.convertMovieFromDto(it)
            }
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    suspend fun loadPopularActorsList() {
        try {
            popularActorsList.value = movieApi.getPopularActors().results.distinct().map {
                DtoMapper.convertActorFromDto(it)
            }
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    fun getPopularActors(): MutableLiveData<List<Actor>> = popularActorsList

    fun getMoviesByGenre(): MutableLiveData<List<Movie>> = moviesByGenre

    fun getGenresList(): MutableLiveData<List<Genre>> = genresList

    fun getMovieVideos(): MutableLiveData<List<MovieVideoDto>> = movieVideos

    fun getCurrentMovie(): MutableLiveData<MovieDetails> = currentMovieDetails

    fun getMovieImages(): MutableLiveData<Map<String, List<MovieImageDto>>> = movieImages

    fun getRecommendations(): MutableLiveData<List<Movie>> = movieRecommendations

    fun getSimilarMovies(): MutableLiveData<List<Movie>> = similarMovies

    fun setAverageColor(body: Int, text: Int) {
        actorImgAverageColorBody.postValue(body)
        actorImgAverageColorText.postValue(text)
    }

    fun getAverageColorBody(): MutableLiveData<Int> = actorImgAverageColorBody
    fun getAverageColorText(): MutableLiveData<Int> = actorImgAverageColorText

    suspend fun loadPopularMoviesFromNet() {
        try {
            popularMoviesList = movieApi.getPopular().results.distinct().map {
                DtoMapper.convertMovieFromDto(
                    it
                )
            }
        } catch (e: Exception) {
            handleExceptions(e)
        }

    }

    suspend fun loadTopRatedMoviesFromNet() {
        try {
            topRatedMoviesList = movieApi.getTopRated().results.distinct().map {
                DtoMapper.convertMovieFromDto(
                    it
                )
            }
        } catch (e: Exception) {
            handleExceptions(e)
        }

    }

    suspend fun loadUpcomingMoviesFromNet() {
        try {
            upcomingMoviesList = movieApi.getUpcoming().results.distinct().map {
                DtoMapper.convertMovieFromDto(
                    it
                )
            }
        } catch (e: Exception) {
            handleExceptions(e)
        }

    }


    fun getTopRatedMovies(): List<Movie> {
        return topRatedMoviesList
    }

    fun getPopularMovies(): List<Movie> {
        return popularMoviesList
    }

    fun getUpcomingMovies(): List<Movie> {
        return upcomingMoviesList
    }

    private fun handleExceptions(e: Exception) {
        throw when (e) {
            is IOException, is HttpException, is TimeoutException -> ConnectionErrorException()
            else -> e
        }
    }


}