package com.johnsondev.doboshacademyapp.data.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.johnsondev.doboshacademyapp.data.db.entities.ActorEntity

interface ActorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(actors: List<ActorEntity>)

}