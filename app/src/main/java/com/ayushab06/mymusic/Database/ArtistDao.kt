package com.ayushab06.mymusic.Database

import androidx.room.*
import com.ayushab06.mymusic.entities.ArtistEntity
import com.ayushab06.mymusic.entities.Relations.ArtistWithSongs
import com.ayushab06.mymusic.entities.SongEntity

@Dao
interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(songEntity: SongEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtist(artistEntity: ArtistEntity)

    @Delete
    suspend fun deleteArtist(artistEntity: ArtistEntity)

    @Transaction
    @Query("SELECT * FROM Artists WHERE Artist = :artist ")
    suspend fun getArtistWithSongs(artist: String):List<ArtistWithSongs>

}