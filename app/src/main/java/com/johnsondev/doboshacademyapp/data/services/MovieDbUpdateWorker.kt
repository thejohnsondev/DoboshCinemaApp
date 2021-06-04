package com.johnsondev.doboshacademyapp.data.services

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.saveUpdateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDbUpdateWorker(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    override suspend fun doWork(): Result {
        return try {
            saveUpdateTime(context)
            scope.launch {
                MoviesRepository.loadMoviesFromNet()
            }
            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }
    }


}