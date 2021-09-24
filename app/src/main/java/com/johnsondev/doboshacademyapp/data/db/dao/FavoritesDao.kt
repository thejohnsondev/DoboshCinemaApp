package com.johnsondev.doboshacademyapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.johnsondev.doboshacademyapp.data.db.entities.FavoriteEntity

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteEntity(favoriteEntity: FavoriteEntity)

    @Query("SELECT entityId FROM favorites WHERE entityType= :type")
    suspend fun getFavoritesEntityIdByType(type: String): List<Int>

    @Query("DELETE FROM favorites WHERE entityType= :type AND entityId= :id")
    suspend fun deleteFavoriteEntity(type: String, id: Int)

    @Query("DELETE FROM favorites WHERE entityType= :type")
    suspend fun deleteAllEntitiesByType(type: String)
}