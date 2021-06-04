package com.johnsondev.doboshacademyapp

import android.app.Application
import androidx.room.Room
import com.johnsondev.doboshacademyapp.data.db.MoviesDatabase
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIES_DB_NAME

class App : Application() {

    private lateinit var movieDatabase: MoviesDatabase

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        movieDatabase = Room.databaseBuilder(
            this,
            MoviesDatabase::class.java,
            MOVIES_DB_NAME
        ).build()
    }

    fun getMovieDatabase(): MoviesDatabase = movieDatabase

    companion object {
        private lateinit var appInstance: App
        fun getInstance(): App = appInstance
    }
}