package com.johnsondev.doboshacademyapp

import android.app.Application
import androidx.room.Room
import com.johnsondev.doboshacademyapp.data.db.FavoritesDb
import com.johnsondev.doboshacademyapp.utilities.Constants.FAVORITES_DB_NAME

class App : Application() {

    //    private lateinit var movieDatabase: MoviesDatabase
    private lateinit var favoritesDatabase: FavoritesDb

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        favoritesDatabase = Room.databaseBuilder(
            this,
            FavoritesDb::class.java,
            FAVORITES_DB_NAME
        ).build()
//        movieDatabase = Room.databaseBuilder(
//            this,
//            MoviesDatabase::class.java,
//            MOVIES_DB_NAME
//        ).build()
    }

    //    fun getMovieDatabase(): MoviesDatabase = movieDatabase
    fun getFavoritesDatabase(): FavoritesDb = favoritesDatabase

    companion object {
        private lateinit var appInstance: App
        fun getInstance(): App = appInstance
    }
}