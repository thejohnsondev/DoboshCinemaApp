package com.johnsondev.doboshacademyapp.data.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.johnsondev.doboshacademyapp.data.db.entities.ActorEntity
import com.johnsondev.doboshacademyapp.data.db.entities.MovieActorJoin

interface MovieActorJoinDao {
    @Query(
        """SELECT * FROM actors
        INNER JOIN MovieWithActor ON actors.id = MovieWithActor.actorId 
        WHERE MovieWithActor.movieId = :movieId"""
    )
    suspend fun getActorsByMovieId(movieId: Int): List<ActorEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(join: MovieActorJoin)
}