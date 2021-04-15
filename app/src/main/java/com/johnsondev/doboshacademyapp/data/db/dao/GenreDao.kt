package com.johnsondev.doboshacademyapp.data.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.johnsondev.doboshacademyapp.data.db.entities.GenreEntity

interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<GenreEntity>)

}