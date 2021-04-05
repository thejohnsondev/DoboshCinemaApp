package com.johnsondev.doboshacademyapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.model.data.Movie
import com.johnsondev.doboshacademyapp.model.dto.MovieDto
import com.johnsondev.doboshacademyapp.network.NetworkService
import com.johnsondev.doboshacademyapp.tools.DtoMapper

object MoviesRepository {

    private val movieApi = NetworkService.MOVIE_API

    private var topRatedMovies = MutableLiveData<List<Movie>>()
    private var topRatedMoviesList: List<MovieDto> = listOf()

    private var popularMovies = MutableLiveData<List<Movie>>()
    private var popularMoviesList: List<MovieDto> = listOf()

    private var upcomingMovies = MutableLiveData<List<Movie>>()
    private var upcomingMoviesList: List<MovieDto> = listOf()

    private var movieById = MutableLiveData<Movie>()

    suspend fun loadTopRatedMovies() {
        topRatedMoviesList = movieApi.getTopRated().results
        topRatedMovies.postValue(topRatedMoviesList.distinct().map {
            DtoMapper.convertMovieFromDto(
                movieApi.getMovieById(it.id)
            )
        })
    }

    suspend fun loadPopularMovies() {
        popularMoviesList = movieApi.getPopular().results
        popularMovies.postValue(popularMoviesList.distinct().map {
            DtoMapper.convertMovieFromDto(
                movieApi.getMovieById(it.id)
            )
        })
    }

    suspend fun loadUpcomingMovies() {
        upcomingMoviesList = movieApi.getUpcoming().results
        upcomingMovies.postValue(upcomingMoviesList.distinct().map {
            DtoMapper.convertMovieFromDto(
                movieApi.getMovieById(it.id)
            )
        })
    }

    suspend fun loadMovieById(id: Int): LiveData<Movie> {
        movieById.postValue(
            DtoMapper.convertMovieFromDto(
                movieApi.getMovieById(id)
            )
        )
        return movieById
    }

    fun getTopRatedMovies(): LiveData<List<Movie>> {
        return topRatedMovies
    }

    fun getLatestMovies(): LiveData<List<Movie>> {
        return popularMovies
    }

    fun getUpcomingMovies(): LiveData<List<Movie>> {
        return upcomingMovies
    }



}