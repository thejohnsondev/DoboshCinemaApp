package com.johnsondev.doboshacademyapp.di

import com.johnsondev.doboshacademyapp.data.network.NetworkService
import com.johnsondev.doboshacademyapp.data.network.api.MovieApi
import com.johnsondev.doboshacademyapp.data.repositories.actors.ActorsRepository
import com.johnsondev.doboshacademyapp.data.repositories.actors.ActorsRepositoryImpl
import com.johnsondev.doboshacademyapp.data.repositories.movies.MoviesRepository
import com.johnsondev.doboshacademyapp.data.repositories.movies.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DataModule::class, NetworkModule::class])
class AppModule

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(api: MovieApi): MoviesRepository = MoviesRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideActorsRepository(api: MovieApi): ActorsRepository = ActorsRepositoryImpl(api)

}

@Module
class NetworkModule {

    @Provides
    fun provideMovieApi(): MovieApi = NetworkService.MOVIE_API

}