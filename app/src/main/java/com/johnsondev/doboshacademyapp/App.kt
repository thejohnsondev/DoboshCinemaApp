package com.johnsondev.doboshacademyapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.johnsondev.doboshacademyapp.data.db.MoviesDb
import java.lang.IllegalStateException

class App : Application() {

    private val DB_NAME = "movies.db"

    private lateinit var database: MoviesDb


    override fun onCreate() {
        super.onCreate()
//        context = applicationContext
        appInstance = this
        database = Room.databaseBuilder(
            this,
            MoviesDb::class.java,
            DB_NAME
        ).build()
    }

    fun getDatabase(): MoviesDb = database

    companion object {

        private lateinit var appInstance: App

        fun getInstance(): App = appInstance


        //        private var context: Context? = null
//        fun getContext(): Context = context ?: throw IllegalStateException()

    }
}