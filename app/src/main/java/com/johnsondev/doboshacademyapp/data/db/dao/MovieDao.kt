package com.johnsondev.doboshacademyapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.johnsondev.doboshacademyapp.data.db.entities.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(moviesList: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE id= :id")
    suspend fun getMovieById(id: Int): MovieEntity

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

}