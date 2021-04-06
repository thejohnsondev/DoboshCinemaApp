package com.johnsondev.doboshacademyapp.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.johnsondev.doboshacademyapp.model.MoviesRepository
import kotlinx.coroutines.*

class SplashScreenActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private val splashTimeOut: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scope.launch {
            MoviesRepository.loadTopRatedMovies()
        }
        scope.launch {
            MoviesRepository.loadPopularMovies()
        }
        scope.launch {
            MoviesRepository.loadUpcomingMovies()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTimeOut)

    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}