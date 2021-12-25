package com.johnsondev.doboshacademyapp.di

import android.app.Application
import androidx.room.Room
import com.johnsondev.doboshacademyapp.data.db.FavoritesDb
import com.johnsondev.doboshacademyapp.data.network.NetworkService
import com.johnsondev.doboshacademyapp.data.network.api.MovieApi
import com.johnsondev.doboshacademyapp.data.repositories.actors.ActorsRepository
import com.johnsondev.doboshacademyapp.data.repositories.actors.ActorsRepositoryImpl
import com.johnsondev.doboshacademyapp.data.repositories.movies.MoviesRepository
import com.johnsondev.doboshacademyapp.data.repositories.movies.MoviesRepositoryImpl
import com.johnsondev.doboshacademyapp.ui.actordetails.ActorDetailsViewModelFactory
import com.johnsondev.doboshacademyapp.ui.favorite.FavoritesViewModelFactory
import com.johnsondev.doboshacademyapp.ui.moviedetails.MovieDetailsViewModelFactory
import com.johnsondev.doboshacademyapp.ui.movielist.MoviesListViewModelFactory
import com.johnsondev.doboshacademyapp.ui.search.SearchViewModelFactory
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.APP_MODULE
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class AppModule(private val application: Application) {

    fun getModule() = Kodein.Module(APP_MODULE) {
        bind<MovieApi>() with singleton { NetworkService.MOVIE_API }
        bind<ActorsRepository>() with singleton { ActorsRepositoryImpl(instance(), instance()) }
        bind<MoviesRepository>() with singleton { MoviesRepositoryImpl(instance(), instance()) }
        bind<FavoritesDb>() with singleton {
            Room.databaseBuilder(
                application,
                FavoritesDb::class.java,
                Constants.FAVORITES_DB_NAME
            ).build()
        }
        bind() from provider { ActorDetailsViewModelFactory(instance(), instance()) }
        bind() from provider { FavoritesViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { MovieDetailsViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { MoviesListViewModelFactory(instance(), instance()) }
        bind() from provider { SearchViewModelFactory(instance(), instance()) }
    }

}