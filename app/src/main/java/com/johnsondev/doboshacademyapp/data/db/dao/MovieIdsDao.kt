package com.johnsondev.doboshacademyapp.data.db.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MovieIdsDao {

    @Query("INSERT INTO movieIds (popularMoviesId) VALUES (:popularMoviesId)")
    suspend fun insertPopularMoviesId(popularMoviesId: String)

    @Query("DELETE FROM movieIds WHERE popularMoviesId")
    suspend fun deleteAllPopularMoviesId()

    @Query("INSERT INTO movieIds (topRatedMoviesId) VALUES (:topRatedMoviesId)")
    suspend fun insertTopRatedMoviesId(topRatedMoviesId: String)

    @Query("DELETE FROM movieIds WHERE topRatedMoviesId")
    suspend fun deleteAllTopRatedMoviesId()

    @Query("INSERT INTO movieIds (upcomingMoviesId) VALUES (:upcomingMoviesId)")
    suspend fun insertUpcomingMoviesId(upcomingMoviesId: String)

    @Query("DELETE FROM movieIds WHERE upcomingMoviesId")
    suspend fun deleteAllUpcomingMoviesId()

    @Query("SELECT popularMoviesId FROM movieIds ")
    suspend fun getPopularMoviesId(): String

    @Query("SELECT topRatedMoviesId FROM movieIds WHERE _id= 2")
    suspend fun getTopRatedMoviesId(): String

    @Query("SELECT upcomingMoviesId FROM movieIds WHERE _id= 3")
    suspend fun getUpcomingMoviesId(): String

}