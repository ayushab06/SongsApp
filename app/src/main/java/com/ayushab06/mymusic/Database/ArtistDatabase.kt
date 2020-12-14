package com.ayushab06.mymusic.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ayushab06.mymusic.entities.ArtistEntity
import com.ayushab06.mymusic.entities.SongEntity

@Database(entities = [ArtistEntity::class,SongEntity::class], version = 3)
abstract class ArtistDatabase: RoomDatabase() {
    abstract fun artistDao(): ArtistDao
}