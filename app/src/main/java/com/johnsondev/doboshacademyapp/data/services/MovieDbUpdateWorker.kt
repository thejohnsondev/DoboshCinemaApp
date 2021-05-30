package com.johnsondev.doboshacademyapp.data.services

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDbUpdateWorker(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    override suspend fun doWork(): Result {
        return try {

            scope.launch {
                MoviesRepository.loadPopularMoviesFromNet()
                MoviesRepository.loadTopRatedMoviesFromNet()
                MoviesRepository.loadUpcomingMoviesFromNet()
            }

            return Result.success()
        } catch (throwable: Throwable) {
            return Result.failure()
        }
    }
}