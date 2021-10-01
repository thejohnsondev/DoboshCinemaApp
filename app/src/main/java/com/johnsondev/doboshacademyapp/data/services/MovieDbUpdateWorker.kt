package com.johnsondev.doboshacademyapp.data.services

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.ui.splash.SplashScreenActivity
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

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        return try {
            var newMovieList: List<Movie>
            scope.launch {

                withContext(scope.coroutineContext) {
                    MoviesRepository.loadUpcomingMoviesFromNet().apply {
                        newMovieList = MoviesRepository.getUpcomingMovies()
                    }
                }

                val newMovie = newMovieList.random()

                buildNotificationChannel()

                if (newMovie.id != 0) {
                    notificationManagerCompat.notify(
                        NOTIFICATION_TAG,
                        newMovie.id,
                        buildNotification(newMovie).build()
                    )
                }
                isNewMovie = false
            }
            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }

    }


    private fun buildNotification(movie: Movie): NotificationCompat.Builder {
        val uri = "${context.getString(R.string.base_deep_link)}${movie.id}".toUri()

        val contentTitle =
            context.getString(R.string.new_movie)

        val futureTarget = Glide.with(context)
            .asBitmap()
            .load(movie.poster)
            .submit()

        val bitMapImg = futureTarget.get()

        return NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setContentTitle(contentTitle)
            setContentText(movie.title)
            setSmallIcon(R.drawable.cinema_app_icon_round)
            priority = NotificationCompat.PRIORITY_LOW
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
            setLargeIcon(bitMapImg)
            setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(bitMapImg)
                .bigLargeIcon(null))
            Glide.with(context).clear(futureTarget)
            setAutoCancel(true)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildNotificationChannel() {
        if (notificationManagerCompat.getNotificationChannel(CHANNEL_ID) == null) {
            notificationManagerCompat.createNotificationChannel(
                NotificationChannelCompat.Builder(
                    CHANNEL_ID,
                    NotificationManagerCompat.IMPORTANCE_DEFAULT
                )
                    .setName(context.getString(R.string.channel_name))
                    .build()
            )
        }
    }

}