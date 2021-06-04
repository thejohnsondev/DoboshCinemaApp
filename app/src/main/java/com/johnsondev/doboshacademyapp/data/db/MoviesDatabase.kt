package com.johnsondev.doboshacademyapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.johnsondev.doboshacademyapp.data.db.dao.*
import com.johnsondev.doboshacademyapp.data.db.entities.*


@Database(
    entities = [
        MovieEntity::class,
        MovieIdsEntity::class,
        GenreEntity::class
    ], version = 1, exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieIdsDao(): MovieIdsDao
    abstract fun genreDao(): GenreDao
}