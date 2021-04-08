package com.johnsondev.doboshacademyapp.network.api

import com.johnsondev.doboshacademyapp.data.dto.MovieDto
import com.johnsondev.doboshacademyapp.data.response.*
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/top_rated")
    suspend fun getTopRated(): MovieResponse

    @GET("movie/popular")
    suspend fun getPopular(): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcoming(): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieById(@Path(value = "movie_id") id: Int): MovieDto

    @GET("movie/{movie_id}/credits")
    suspend fun getActors(@Path(value = "movie_id") id: Int): CastResponse



}