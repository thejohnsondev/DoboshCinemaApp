package com.johnsondev.doboshacademyapp.data.services

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.saveUpdateTime
import kotlinx.coroutines.*
import java.util.*

class MovieDbUpdateWorker(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    override suspend fun doWork(): Result {
        return try {
            var newMovieList: List<Movie>
            var oldMovieList: List<Movie>
            scope.launch {

                withContext(scope.coroutineContext) {
                    oldMovieList = MoviesRepository.loadAllMovieFromDb()
                }
                withContext(scope.coroutineContext) {
                    MoviesRepository.loadMoviesFromNet().apply {
                        newMovieList = MoviesRepository.getAllMoviesFromNet()
                    }
                }
                val newMovie =
                    findNewMovie(oldMovieList, newMovieList).sortedBy { it.ratings }.last()
                saveUpdateTime(context)

            }
            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }

    }

    private fun findNewMovie(oldMovieList: List<Movie>, newMovieList: List<Movie>): List<Movie> {
        val newMovie = mutableListOf<Movie>()
        newMovieList.forEach { new ->
            if (!oldMovieList.contains(new)) {
                newMovie.add(new)
            }
        }
        return if (newMovie.isNullOrEmpty()) {
            newMovie.add(oldMovieList.sortedByDescending { it.ratings }[0])
            newMovie
        } else {
            newMovie
        }
    }
}