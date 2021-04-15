package com.johnsondev.doboshacademyapp.data.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.johnsondev.doboshacademyapp.data.db.entities.MovieGenreJoin

interface MovieGenreJoinDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(join: MovieGenreJoin)

}