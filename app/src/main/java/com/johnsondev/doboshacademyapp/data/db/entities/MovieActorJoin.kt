package com.johnsondev.doboshacademyapp.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


@Entity(
    primaryKeys = ["movieId", "actorId"],
    indices = [
        Index(value = ["movieId"]),
        Index(value = ["actorId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["movieId"],
            childColumns = ["movieId"]
        ),
        ForeignKey(
            entity = ActorEntity::class,
            parentColumns = ["actorId"],
            childColumns = ["actorId"]
        )
    ],
    tableName = "movieWithActor"
)
data class MovieActorJoin(
    val movieId: Int,
    val actorId: Int
)