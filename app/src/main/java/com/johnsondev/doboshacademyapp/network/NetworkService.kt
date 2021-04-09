package com.johnsondev.doboshacademyapp.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.johnsondev.doboshacademyapp.network.api.MovieApi
import com.johnsondev.doboshacademyapp.network.interceptors.ApiKeyInterceptor
import com.johnsondev.doboshacademyapp.utilities.Constants.BASE_URL
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

object NetworkService {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor())
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val MOVIE_API = retrofit.create<MovieApi>()
}