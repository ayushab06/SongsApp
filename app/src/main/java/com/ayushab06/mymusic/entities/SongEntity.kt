package com.ayushab06.mymusic.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SongEntity")
data class SongEntity(
    @ColumnInfo var artistName: String,
    @PrimaryKey val trackId: Int,
    @ColumnInfo val trackName: String,
    @ColumnInfo val trackViewUrl: String
)