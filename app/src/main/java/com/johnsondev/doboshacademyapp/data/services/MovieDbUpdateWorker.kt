package com.johnsondev.doboshacademyapp.data.services

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.util.Util
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.Constants.PREF_UPDATE_TIME
import com.johnsondev.doboshacademyapp.utilities.saveUpdateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class MovieDbUpdateWorker(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    override suspend fun doWork(): Result {
        return try {

            saveUpdateTime(context)
            
            scope.launch {
//                MoviesRepository.loadPopularMoviesFromNet()
//                MoviesRepository.loadTopRatedMoviesFromNet()
//                MoviesRepository.loadUpcomingMoviesFromNet()
                MoviesRepository.loadMoviesFromNet()
            }

            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }
    }


}