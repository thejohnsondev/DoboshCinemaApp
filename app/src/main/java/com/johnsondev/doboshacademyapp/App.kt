package com.johnsondev.doboshacademyapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.johnsondev.doboshacademyapp.data.db.MoviesDatabase
import com.johnsondev.doboshacademyapp.data.db.MoviesDb
import java.lang.IllegalStateException

class App : Application() {

    private val DB_NAME = "movies.db"
    private val MOVIES_DB_NAME = "moviesdatabase.db"

    private lateinit var database: MoviesDb

    private lateinit var movieDatabase: MoviesDatabase

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        database = Room.databaseBuilder(
            this,
            MoviesDb::class.java,
            DB_NAME
        ).build()
        movieDatabase = Room.databaseBuilder(
            this,
            MoviesDatabase::class.java,
            MOVIES_DB_NAME
        ).build()
    }

    fun getDatabase(): MoviesDb = database
    fun getMovieDatabase(): MoviesDatabase = movieDatabase

    companion object {

        private lateinit var appInstance: App
        fun getInstance(): App = appInstance

    }
}