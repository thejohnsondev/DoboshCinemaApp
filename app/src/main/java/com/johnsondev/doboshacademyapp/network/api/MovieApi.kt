package com.johnsondev.doboshacademyapp.network.api

import com.johnsondev.doboshacademyapp.model.response.MovieTopRatedResponse
import com.johnsondev.doboshacademyapp.model.dto.MovieDto
import com.johnsondev.doboshacademyapp.model.response.CastResponse
import com.johnsondev.doboshacademyapp.model.response.MoviePopularResponse
import com.johnsondev.doboshacademyapp.model.response.MovieUpcomingResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/top_rated")
    suspend fun getTopRated(): MovieTopRatedResponse

    @GET("movie/popular")
    suspend fun getPopular(): MoviePopularResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieById(@Path(value = "movie_id") id: Int): MovieDto

    @GET("movie/{movie_id}/credits")
    suspend fun getActors(@Path(value = "movie_id") id: Int): CastResponse

    @GET("movie/upcoming")
    suspend fun getUpcoming(): MovieUpcomingResponse

}