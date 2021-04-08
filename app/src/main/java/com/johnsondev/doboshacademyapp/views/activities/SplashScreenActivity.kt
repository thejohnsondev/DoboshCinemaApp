package com.johnsondev.doboshacademyapp.views.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.InternetConnectionManager
import com.johnsondev.doboshacademyapp.utilities.Constants
import kotlinx.coroutines.*


class SplashScreenActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    private lateinit var checkInternetConnection: InternetConnectionManager

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainActivity::class.java)

        checkInternetConnection = InternetConnectionManager(this)

        if (checkInternetConnection.isNetworkAvailable()) {

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
                intent.putExtra(Constants.CONNECTION_ERROR_EXTRA, false)
                startActivity(intent)
                finish()
            }

        } else {

            intent.putExtra(Constants.CONNECTION_ERROR_EXTRA, true)
            startActivity(intent)
            finish()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

}