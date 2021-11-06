package com.johnsondev.doboshacademyapp.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.network.exception.ConnectionErrorException
import com.johnsondev.doboshacademyapp.data.repositories.movies.MoviesRepository
import com.johnsondev.doboshacademyapp.data.repositories.movies.MoviesRepositoryImpl
import com.johnsondev.doboshacademyapp.utilities.InternetConnectionManager
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_KEY
import com.johnsondev.doboshacademyapp.ui.movielist.ListActivity
import com.johnsondev.doboshacademyapp.utilities.appComponent
import kotlinx.coroutines.*
import javax.inject.Inject

class SplashScreenActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private lateinit var checkInternetConnection: InternetConnectionManager

    @Inject
    lateinit var moviesRepository: MoviesRepository

    private var movieId: Int? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listActivityIntent = Intent(this, ListActivity::class.java)

        appComponent().inject(this)

        checkInternetConnection = InternetConnectionManager(this)
        scope.launch {
            if (checkInternetConnection.isNetworkAvailable()) {
                scope.launch {
                    try {
                        moviesRepository.loadPopularMoviesFromNet()
                        moviesRepository.loadTopRatedMoviesFromNet()
                        moviesRepository.loadUpcomingMoviesFromNet()
                    } catch (e: Exception) {
                        when (e) {
                            is ConnectionErrorException -> {
                                openNextActivity(listActivityIntent)
                            }
                        }
                    }
                }.join()
                if (intent != null) {
                    handleIntent(intent)
                    listActivityIntent.putExtra(MOVIE_KEY, movieId)
                    startActivity(listActivityIntent)
                    finish()
                } else {
                    startActivity(listActivityIntent)
                    finish()
                }
            } else {
                openNextActivity(listActivityIntent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                movieId = intent.data?.lastPathSegment?.toIntOrNull()
            }
        }
    }

    private fun openNextActivity(intent: Intent) {
        intent.putExtra(Constants.CONNECTION_ERROR_EXTRA, true)
        startActivity(intent)
        finish()
    }


}