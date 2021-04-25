package com.johnsondev.doboshacademyapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.johnsondev.doboshacademyapp.data.db.entities.GenreEntity
import com.johnsondev.doboshacademyapp.data.models.Genre

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<GenreEntity>)

    @Query("SELECT * FROM genres WHERE id = :id")
    suspend fun selectGenresById(id: Int): List<GenreEntity>

    @Query("SELECT * FROM genres")
    suspend fun selectAllGenres(): List<GenreEntity>

}