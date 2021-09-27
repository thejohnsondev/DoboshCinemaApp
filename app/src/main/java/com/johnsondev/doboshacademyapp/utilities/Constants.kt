package com.johnsondev.doboshacademyapp.utilities

import com.johnsondev.doboshacademyapp.BuildConfig
import java.util.*

object Constants {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val POSTER_PATH = "https://image.tmdb.org/t/p/w500"
    const val BACKDROP_PATH = "https://image.tmdb.org/t/p/w1280"
    const val API_KEY = BuildConfig.API_KEY

    const val CALENDAR_PATH = "vnd.android.cursor.item/event"
    const val CALENDAR_VAL_BEGIN_TIME = "beginTime"
    const val CALENDAR_VAL_ALL_DAY = "allDay"
    const val CALENDAR_VAL_END_TIME = "endTime"
    const val CALENDAR_VAL_TITLE = "title"
    const val CALENDAR_VAL_DESCRIPTION = "description"

    const val HORIZONTAL_SPAN_COUNT = 4
    const val VERTICAL_SPAN_COUNT = 5

    const val CONNECTION_ERROR_EXTRA = "connection_error_extra"
    const val CONNECTION_ERROR_ARG = "connection_error_arg"

    const val PREF_UPDATE_TIME = "pref_update_time"
    const val PERIODIC_UPDATE_WORK = "periodic_update_work"

    const val SPECIFIC_LIST_TYPE = "spec_list_type"
    const val POPULAR_SPEC_TYPE = "popular_spec_type"
    const val TOP_RATED_SPEC_TYPE = "top_rated_spec_type"
    const val UPCOMING_SPEC_TYPE = "upcoming_spec_type"
    const val GENRE_SPEC_TYPE = "genre_spec_type"
    const val SEARCH_RESULT_SPEC_TYPE = "input_data_spec_type"
    const val MOVIES_SEARCH_RESULT_SPEC_TYPE = "movies_search_result_spec_type"
    const val ACTORS_SEARCH_RESULT_SPEC_TYPE = "actors_search_result_spec_type"
    const val POP_ACTORS_SPEC_TYPE = "pop_actors_spec_type"

    const val ITEM_TYPE_MINI = 0
    const val ITEM_TYPE_HORIZONTAL = 1

    const val MOVIE_ITEM_MINI = 8
    const val MOVIE_ITEM_DEFAULT = 9
    const val MOVIE_ITEM_LARGE = 10

    const val IMAGES_LIST_TYPE = "images_list_type"
    const val ITEM_TYPE_POSTER = 2
    const val ITEM_TYPE_BACKDROP = 3

    const val ACTOR_KEY = "actor_key"
    const val MOVIE_KEY = "movie_key"
    const val GENRE_KEY = "genre_key"

    const val BACKDROP_KEY = "backdrop_key"
    const val POSTER_KEY = "poster_key"

    const val RECOMMENDATIONS_LIST_TYPE: String = "recommendation_list_type"
    const val SIMILAR_LIST_TYPE: String = "similar_list_type"

    const val FAVORITES_DB_NAME = "favoritesdatabase.db"
    const val TABLE_FAVORITES = "favorites"
    const val FAVORITE_MOVIE_ENTITY_TYPE = "favorite_movie_entity_type"
    const val FAVORITE_ACTOR_ENTITY_TYPE = "favorite_actor_entity_type"

    const val LANG_ENG = "en-US"
    const val LANG_RU = "ru-RU"

    val MOVIE_TAB_TITLES =
        arrayListOf("Information", "Actors", "Recommendations", "Similar", "Trailers")
    val ACTOR_TAB_TITLES = arrayListOf("About", "Movies")
    val FAVORITES_TAB_TITLES = arrayListOf("Movies","Actors")

    val CURRENT_YEAR = Calendar.getInstance()[Calendar.YEAR]
    const val EMPTY_STRING = ""
    const val ANIM_PROPERTY_MAX_LINES = "maxLines"

}