package com.johnsondev.doboshacademyapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.johnsondev.doboshacademyapp.data.db.entities.TopRatedMoviesEntity

@Dao
interface TopRatedMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(topRatedMovies: TopRatedMoviesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(topRatedMovies: List<TopRatedMoviesEntity>)

    @Query("SELECT * FROM topRatedMovies")
    suspend fun getAllMovies(): List<TopRatedMoviesEntity>

}