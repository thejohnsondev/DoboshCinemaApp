package com.johnsondev.doboshacademyapp.data.db.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MovieIdsDao {

    @Query("INSERT INTO movieIds(listType, movieIdsList) VALUES (:listType, :movieIdsList)")
    suspend fun insertMovieIdsList(listType: String, movieIdsList: String)

    @Query("SELECT movieIdsList FROM movieIds WHERE listType= :listType")
    suspend fun getMovieIdsList(listType: String): String

    @Query("DELETE FROM movieIds WHERE listType= :listType")
    suspend fun deleteMovieIdsList(listType: String)

}