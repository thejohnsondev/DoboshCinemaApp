package com.johnsondev.doboshacademyapp.data.network.api

import com.johnsondev.doboshacademyapp.data.network.dto.ActorDetailsDto
import com.johnsondev.doboshacademyapp.data.network.dto.GenresListResponse
import com.johnsondev.doboshacademyapp.data.network.dto.MovieDetailsDto
import com.johnsondev.doboshacademyapp.data.network.dto.MovieDto
import com.johnsondev.doboshacademyapp.data.network.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/top_rated")
    suspend fun getTopRated(): MovieResponse

    @GET("movie/popular")
    suspend fun getPopular(): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcoming(): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieById(@Path(value = "movie_id") id: Int): MovieDetailsDto

    @GET("movie/{movie_id}/credits")
    suspend fun getActors(@Path(value = "movie_id") id: Int): CastResponse

    @GET("person/{person_id}")
    suspend fun getActorDetails(@Path(value = "person_id") id: Int): ActorDetailsDto

    @GET("person/{person_id}/movie_credits")
    suspend fun getActorMovieCredits(@Path(value = "person_id") id: Int): ActorMovieCreditsResponse

    @GET("person/{person_id}/images")
    suspend fun getActorImages(@Path(value = "person_id") id: Int): ActorImagesResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path(value = "movie_id") id: Int,
        @Query("language") lang: String
    ): MovieVideosResponse

    @GET("genre/movie/list")
    suspend fun getGenresList(): GenresListResponse

    @GET("discover/movie")
    suspend fun getMoviesListByGenreId(@Query("with_genres")id: Int): MovieResponse

    @GET("person/popular")
    suspend fun getPopularActors(): ActorsListResponse
}