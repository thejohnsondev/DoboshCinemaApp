package com.johnsondev.doboshacademyapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.johnsondev.doboshacademyapp.data.db.dao.*
import com.johnsondev.doboshacademyapp.data.db.entities.*
import com.johnsondev.doboshacademyapp.utilities.IdConverter


@Database(
    entities = [
        MovieEntity::class,
        MovieIdsEntity::class
    ], version = 1, exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieIdsDao(): MovieIdsDao
}