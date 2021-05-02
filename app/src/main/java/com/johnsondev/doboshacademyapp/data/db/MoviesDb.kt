package com.johnsondev.doboshacademyapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.johnsondev.doboshacademyapp.data.db.dao.*
import com.johnsondev.doboshacademyapp.data.db.entities.*

@Database(
    entities = [
        PopularMoviesEntity::class,
        TopRatedMoviesEntity::class,
        UpcomingMoviesEntity::class,
        GenreEntity::class
    ], version = 1
)
abstract class MoviesDb : RoomDatabase() {

    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun topRatedMoviesDao(): TopRatedMoviesDao
    abstract fun upcomingMoviesDao(): UpcomingMoviesDao
    abstract fun genreDao(): GenreDao

}