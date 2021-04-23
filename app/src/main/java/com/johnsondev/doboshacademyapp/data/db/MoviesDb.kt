package com.johnsondev.doboshacademyapp.data.db

import android.content.Context
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
        GenreEntity::class
    ], version = 1
)
abstract class MoviesDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun actorDao(): ActorDao
    abstract fun genreDao(): GenreDao

//    companion object {
//
//        private const val DB_NAME = "movies.db"
//
//        @Volatile
//        private var dbInstance: MoviesDb? = null

//        @Volatile
//        private var instance: MoviesDb? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: buildDatabase(context).also { instance = it }
//        }
//
//        private fun buildDatabase(context: Context) = Room.databaseBuilder(
//            context,
//            MoviesDb::class.java, DB_NAME
//        ).build()
//
//        fun getDatabase(): MoviesDb {
//            return dbInstance ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    App.getContext(),
//                    MoviesDb::class.java,
//                    DB_NAME
//                ).build()
//                dbInstance = instance
//                return instance
//            }
//        }
//    }
}