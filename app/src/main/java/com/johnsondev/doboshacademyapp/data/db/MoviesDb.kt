package com.johnsondev.doboshacademyapp.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.johnsondev.doboshacademyapp.App
import com.johnsondev.doboshacademyapp.data.db.dao.*
import com.johnsondev.doboshacademyapp.data.db.entities.*

@Database(
    entities = [
        MovieEntity::class,
        ActorEntity::class,
        GenreEntity::class,
        MovieActorJoin::class,
        MovieGenreJoin::class
    ], version = 1
)
abstract class MoviesDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun actorDao(): ActorDao
    abstract fun genreDao(): GenreDao
    abstract fun movieWithActorDao(): MovieActorJoinDao
    abstract fun movieWithGenreDao(): MovieGenreJoinDao

    companion object {

        private const val DB_NAME = "movies.db"

        private var dbInstance: MoviesDb? = null

        fun getDatabase(): MoviesDb {
            return dbInstance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    App.getContext(),
                    MoviesDb::class.java,
                    DB_NAME
                ).build()
                dbInstance = instance
                return instance
            }
        }
    }
}