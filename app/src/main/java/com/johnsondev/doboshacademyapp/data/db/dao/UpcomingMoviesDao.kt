package com.johnsondev.doboshacademyapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.johnsondev.doboshacademyapp.data.db.entities.TopRatedMoviesEntity
import com.johnsondev.doboshacademyapp.data.db.entities.UpcomingMoviesEntity

@Dao
interface UpcomingMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(upcomingMovies: UpcomingMoviesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(upcomingMovies: List<UpcomingMoviesEntity>)

    @Query("SELECT * FROM upcomingMovies")
    suspend fun getAllMovies(): List<UpcomingMoviesEntity>

    @Query("DELETE FROM upcomingMovies")
    suspend fun deleteAllMovies()
}