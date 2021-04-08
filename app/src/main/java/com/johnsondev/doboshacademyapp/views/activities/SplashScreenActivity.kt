package com.johnsondev.doboshacademyapp.views.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.Constants
import kotlinx.coroutines.*
import kotlin.math.log

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var cManager: ConnectivityManager

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainActivity::class.java)

        cManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (isNetworkAvailable()) {

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

        } else {

            intent.putExtra(Constants.CONNECTION_ERROR_EXTRA, true)
            startActivity(intent)
            finish()

        }
    }

    private fun isNetworkAvailable(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = cManager.getNetworkCapabilities(cManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
                }
            }
        } else {
            val activeNetworkInfo = cManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }


    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

}