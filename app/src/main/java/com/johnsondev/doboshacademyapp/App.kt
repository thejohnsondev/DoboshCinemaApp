package com.johnsondev.doboshacademyapp

import android.app.Application
import android.content.Context
import java.lang.IllegalStateException

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        private var context: Context? = null
        fun getContext(): Context = context ?: throw IllegalStateException()
    }
}