package com.johnsondev.doboshacademyapp.utilities

import com.johnsondev.doboshacademyapp.BuildConfig

object Constants {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val POSTER_PATH = "https://image.tmdb.org/t/p/w342"
    const val ACTOR_IMG_PATH = "https://image.tmdb.org/t/p/w92"
    const val API_KEY = BuildConfig.API_KEY

    const val HORIZONTAL_SPAN_COUNT = 4
    const val VERTICAL_SPAN_COUNT = 2

    const val CONNECTION_ERROR_EXTRA = "connection_error_extra"
    const val CONNECTION_ERROR_ARG = "connection_error_arg"
    const val MOVIE_KEY = "movie_key"
    const val PREF_UPDATE_TIME = "pref_update_time"
    const val PERIODIC_UPDATE_WORK = "periodic_update_work"

    const val POPULAR_MOVIES_TYPE: String = "popular"
    const val TOP_RATED_MOVIES_TYPE: String = "topRated"
    const val UPCOMING_MOVIES_TYPE: String = "upcoming"

    const val MOVIES_DB_NAME = "moviesdatabase.db"

}