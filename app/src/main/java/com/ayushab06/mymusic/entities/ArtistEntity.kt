package com.ayushab06.mymusic.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Artists")
data class ArtistEntity(
    @PrimaryKey val Artist:String,
)
