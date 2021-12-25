package com.johnsondev.doboshacademyapp

import android.app.Application
import androidx.room.Room
import com.johnsondev.doboshacademyapp.data.db.FavoritesDb
import com.johnsondev.doboshacademyapp.data.network.NetworkService
import com.johnsondev.doboshacademyapp.data.network.api.MovieApi
import com.johnsondev.doboshacademyapp.data.repositories.actors.ActorsRepositoryImpl
import com.johnsondev.doboshacademyapp.data.repositories.movies.MoviesRepositoryImpl
import com.johnsondev.doboshacademyapp.data.repositories.actors.ActorsRepository
import com.johnsondev.doboshacademyapp.data.repositories.movies.MoviesRepository
import com.johnsondev.doboshacademyapp.di.AppModule
import com.johnsondev.doboshacademyapp.ui.actordetails.ActorDetailsViewModelFactory
import com.johnsondev.doboshacademyapp.ui.favorite.FavoritesViewModelFactory
import com.johnsondev.doboshacademyapp.ui.moviedetails.MovieDetailsViewModelFactory
import com.johnsondev.doboshacademyapp.ui.movielist.MoviesListViewModelFactory
import com.johnsondev.doboshacademyapp.ui.search.SearchViewModelFactory
import com.johnsondev.doboshacademyapp.utilities.Constants.FAVORITES_DB_NAME
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))
        import(AppModule(this@App).getModule())
    }

}