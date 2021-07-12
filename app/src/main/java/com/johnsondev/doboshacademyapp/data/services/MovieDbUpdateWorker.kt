package com.johnsondev.doboshacademyapp.data.services

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.saveUpdateTime
import com.johnsondev.doboshacademyapp.views.activities.SplashScreenActivity
import kotlinx.coroutines.*

class MovieDbUpdateWorker(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    companion object {
        private const val CHANNEL_ID = "MOVIE_CHANNEL_ID"
        private const val REQUEST_CONTENT = 1
        private const val NOTIFICATION_TAG = "newMovies"
    }

    private val notificationManagerCompat = NotificationManagerCompat.from(context)
    private var isNewMovie = false
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
//                val newMovie =
//                    findNewMovie(oldMovieList, newMovieList).sortedBy { it.ratings }.last()

                val newMovie = findNewMovie(oldMovieList, newMovieList)

                buildNotificationChannel()

                if (newMovie.id != 0) {
                    notificationManagerCompat.notify(
                        NOTIFICATION_TAG,
                        newMovie.id,
                        buildNotification(newMovie).build()
                    )
                }
                isNewMovie = false
                saveUpdateTime(context)
            }
            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }

    }

    private fun findNewMovie(oldMovieList: List<Movie>, newMovieList: List<Movie>): Movie {
        var newMovie: Movie? = null
        newMovieList.forEach { new ->
            val isNewMovieExisting = !oldMovieList.any { it.id == new.id }
            if (isNewMovieExisting) {
                newMovie = new
                return@forEach
            }
        }
        return newMovie ?: Movie(id = 0)
    }

    private fun buildNotification(movie: Movie): NotificationCompat.Builder {
        val uri = "${context.getString(R.string.base_deep_link)}${movie.id}".toUri()

        val contentTitle =
            context.getString(R.string.new_movie)

        return NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setContentTitle(contentTitle)
            setContentText(movie.title)
            setSmallIcon(R.drawable.ic_baseline_videocam_24)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setOnlyAlertOnce(true)
            setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT,
                    Intent(context, SplashScreenActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(uri),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            setAutoCancel(true)
        }
    }

    private fun buildNotificationChannel() {
        if (notificationManagerCompat.getNotificationChannel(CHANNEL_ID) == null) {
            notificationManagerCompat.createNotificationChannel(
                NotificationChannelCompat.Builder(
                    CHANNEL_ID,
                    NotificationManagerCompat.IMPORTANCE_HIGH
                )
                    .setName(context.getString(R.string.channel_name))
                    .build()
            )
        }
    }

}