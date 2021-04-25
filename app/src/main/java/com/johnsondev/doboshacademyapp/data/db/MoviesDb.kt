package com.johnsondev.doboshacademyapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.johnsondev.doboshacademyapp.data.db.dao.*
import com.johnsondev.doboshacademyapp.data.db.entities.*

@Database(
    entities = [
        MovieEntity::class,
        ActorEntity::class,
        GenreEntity::class
    ], version = 1
)
abstract class MoviesDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun actorDao(): ActorDao
    abstract fun genreDao(): GenreDao

}