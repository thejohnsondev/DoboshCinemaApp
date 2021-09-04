package com.johnsondev.doboshacademyapp.utilities

import com.johnsondev.doboshacademyapp.BuildConfig

object Constants {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val POSTER_PATH = "https://image.tmdb.org/t/p/w342"
    const val API_KEY = BuildConfig.API_KEY

    const val CALENDAR_PATH = "vnd.android.cursor.item/event"
    const val CALENDAR_VAL_BEGIN_TIME = "beginTime"
    const val CALENDAR_VAL_ALL_DAY = "allDay"
    const val CALENDAR_VAL_END_TIME = "endTime"
    const val CALENDAR_VAL_TITLE = "title"
    const val CALENDAR_VAL_DESCRIPTION = "description"

    const val HORIZONTAL_SPAN_COUNT = 4
    const val VERTICAL_SPAN_COUNT = 2

    const val CONNECTION_ERROR_EXTRA = "connection_error_extra"
    const val CONNECTION_ERROR_ARG = "connection_error_arg"

    const val PREF_UPDATE_TIME = "pref_update_time"
    const val PERIODIC_UPDATE_WORK = "periodic_update_work"

    const val SPECIFIC_LIST_TYPE = "spec_list_type"
    const val POPULAR_SPEC_TYPE = "popular_spec_type"
    const val TOP_RATED_SPEC_TYPE = "top_rated_spec_type"
    const val UPCOMING_SPEC_TYPE = "upcoming_spec_type"
    const val GENRE_SPEC_TYPE = "genre_spec_type"
    const val POP_ACTORS_SPEC_TYPE = "pop_actors_spec_type"

    const val ITEM_TYPE_MINI = 0
    const val ITEM_TYPE_HORIZONTAL = 1

    const val IMAGES_LIST_TYPE = "images_list_type"
    const val ITEM_TYPE_POSTER = 2
    const val ITEM_TYPE_BACKDROP = 3

    const val ACTOR_KEY = "actor_key"
    const val TRAILERS_KEY = "trailers_key"
    const val MOVIE_KEY = "movie_key"
    const val GENRE_KEY = "genre_key"

    const val BACKDROP_KEY = "backdrop_key"
    const val POSTER_KEY = "poster_key"

    const val POPULAR_MOVIES_TYPE: String = "popular"
    const val TOP_RATED_MOVIES_TYPE: String = "topRated"
    const val UPCOMING_MOVIES_TYPE: String = "upcoming"

    const val MOVIES_DB_NAME = "moviesdatabase.db"

    const val LANG_ENG = "en-US"
    const val LANG_RU = "ru-RU"

    val TAB_TITLES = arrayListOf("Information", "Actors", "Recommendations", "Similar", "Trailers")

}