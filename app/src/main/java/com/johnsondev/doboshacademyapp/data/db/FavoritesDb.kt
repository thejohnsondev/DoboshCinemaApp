package com.johnsondev.doboshacademyapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.johnsondev.doboshacademyapp.data.db.dao.FavoritesDao
import com.johnsondev.doboshacademyapp.data.db.entities.FavoriteEntity


@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false)
abstract class FavoritesDb : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}