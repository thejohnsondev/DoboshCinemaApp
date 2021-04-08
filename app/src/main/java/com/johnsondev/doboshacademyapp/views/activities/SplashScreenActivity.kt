package com.johnsondev.doboshacademyapp.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import kotlinx.coroutines.*

class SplashScreenActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainActivity::class.java)

        scope.launch {
            val job1 = scope.launch {
                MoviesRepository.loadPopularMovies()
            }
            val job2 = scope.launch {
                MoviesRepository.loadTopRatedMovies()
            }
            val job3 = scope.launch {
                MoviesRepository.loadUpcomingMovies()
            }
            job1.join()
            job2.join()
            job3.join()
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

}