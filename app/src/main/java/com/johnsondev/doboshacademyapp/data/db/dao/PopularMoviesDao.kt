package com.johnsondev.doboshacademyapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.johnsondev.doboshacademyapp.data.db.entities.PopularMoviesEntity

@Dao
interface PopularMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(popularMovies: PopularMoviesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(popularMovies: List<PopularMoviesEntity>)

    @Query("SELECT * FROM popularMovies")
    suspend fun getAllMovies(): List<PopularMoviesEntity>

}