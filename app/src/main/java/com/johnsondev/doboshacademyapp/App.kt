package com.johnsondev.doboshacademyapp

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.johnsondev.doboshacademyapp.data.db.FavoritesDb
import com.johnsondev.doboshacademyapp.di.AppComponent
import com.johnsondev.doboshacademyapp.di.DaggerAppComponent
import com.johnsondev.doboshacademyapp.utilities.Constants.FAVORITES_DB_NAME

class App : Application() {

    private lateinit var favoritesDatabase: FavoritesDb
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        appComponent = DaggerAppComponent.create()
        favoritesDatabase = Room.databaseBuilder(
            this,
            FavoritesDb::class.java,
            FAVORITES_DB_NAME
        ).build()

    }

    fun getFavoritesDatabase(): FavoritesDb = favoritesDatabase

    companion object {
        private lateinit var appInstance: App
        fun getInstance(): App = appInstance
    }
}