package com.johnsondev.doboshacademyapp.views.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.johnsondev.doboshacademyapp.App
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.InternetConnectionManager
import com.johnsondev.doboshacademyapp.utilities.Constants
import kotlinx.coroutines.*


class SplashScreenActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    private lateinit var checkInternetConnection: InternetConnectionManager

    private val database = App.getInstance().getDatabase()


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainActivity::class.java)

        checkInternetConnection = InternetConnectionManager(this)
        scope.launch {
            if (database.popularMoviesDao().getAllMovies().isNotEmpty()
                && database.topRatedMoviesDao().getAllMovies().isNotEmpty()
                && database.upcomingMoviesDao().getAllMovies().isNotEmpty()
            ) {

                scope.launch {
                    MoviesRepository.loadPopularMoviesFromDb()
                    MoviesRepository.loadTopRatedMoviesFromDb()
                    MoviesRepository.loadUpcomingMoviesFromDb()
                }.join()

                startActivity(intent)
                finish()
            } else {
                if (checkInternetConnection.isNetworkAvailable()) {
                    scope.launch {
                        MoviesRepository.loadPopularMoviesFromNet()
                        MoviesRepository.loadTopRatedMoviesFromNet()
                        MoviesRepository.loadUpcomingMoviesFromNet()
                    }.join()

                    intent.putExtra(Constants.CONNECTION_ERROR_EXTRA, true)
                    startActivity(intent)
                    finish()
                } else {
                    intent.putExtra(Constants.CONNECTION_ERROR_EXTRA, true)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

}