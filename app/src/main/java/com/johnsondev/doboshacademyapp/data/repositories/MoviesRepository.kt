package com.johnsondev.doboshacademyapp.data.repositories

import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.App
import com.johnsondev.doboshacademyapp.data.models.Genre
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.network.NetworkService
import com.johnsondev.doboshacademyapp.data.network.dto.MovieVideoDto
import com.johnsondev.doboshacademyapp.data.network.exception.ConnectionErrorException
import com.johnsondev.doboshacademyapp.data.network.exception.UnexpectedErrorException
import com.johnsondev.doboshacademyapp.utilities.Constants.LANG_RU
import com.johnsondev.doboshacademyapp.utilities.Constants.POPULAR_MOVIES_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.TOP_RATED_MOVIES_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.UPCOMING_MOVIES_TYPE
import com.johnsondev.doboshacademyapp.utilities.Converter
import com.johnsondev.doboshacademyapp.utilities.DtoMapper
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeoutException

object MoviesRepository {

    private val movieApi = NetworkService.MOVIE_API

    private var popularMoviesList: List<Movie> = listOf()
    private var topRatedMoviesList: List<Movie> = listOf()
    private var upcomingMoviesList: List<Movie> = listOf()



    private var actorImgAverageColorBody = MutableLiveData<Int>()
    private var actorImgAverageColorText = MutableLiveData<Int>()

    private var movieVideos = MutableLiveData<List<MovieVideoDto>>()

    private var currentMovie = MutableLiveData<Movie>()

    suspend fun loadMovieVideosById(id: Int) {
        try {
            movieVideos.postValue(movieApi.getMovieVideos(id, LANG_RU).results)
        }catch (e: Exception){
            handleExceptions(e)
        }

    }

    suspend fun loadMovieById(id: Int) {
        try {
            currentMovie.value = DtoMapper.convertMovieFromDto(movieApi.getMovieById(id))
        }catch (e: Exception){
            handleExceptions(e)
        }
    }

    fun getMovieVideos(): MutableLiveData<List<MovieVideoDto>> = movieVideos

    fun getCurrentMovie(): MutableLiveData<Movie> = currentMovie

    fun setAverageColor(body: Int, text: Int) {
        actorImgAverageColorBody.postValue(body)
        actorImgAverageColorText.postValue(text)
    }

    fun getAverageColorBody(): MutableLiveData<Int> = actorImgAverageColorBody
    fun getAverageColorText(): MutableLiveData<Int> = actorImgAverageColorText


    suspend fun loadMoviesFromNet() {

        try {
            popularMoviesList = movieApi.getPopular().results.distinct().map {
                DtoMapper.convertMovieFromDto(
                    movieApi.getMovieById(it.id)
                )
            }

            topRatedMoviesList =
                movieApi.getTopRated().results.distinct().map {
                    DtoMapper.convertMovieFromDto(
                        movieApi.getMovieById(it.id)
                    )
                }

            upcomingMoviesList = movieApi.getUpcoming().results.distinct().map {
                DtoMapper.convertMovieFromDto(
                    movieApi.getMovieById(it.id)
                )
            }
        }catch (e: Exception){
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

    private fun handleExceptions(e: Exception){
        throw when(e){
            is IOException, is HttpException, is TimeoutException -> ConnectionErrorException()
            else -> UnexpectedErrorException()
        }
    }


}