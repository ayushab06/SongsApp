package com.ayushab06.mymusic.entities.Relations

import androidx.room.Embedded
import androidx.room.Relation
import com.ayushab06.mymusic.entities.ArtistEntity
import com.ayushab06.mymusic.entities.SongEntity

data class ArtistWithSongs(
    @Embedded val artist:ArtistEntity,
    @Relation(
        parentColumn = "Artist",
        entityColumn = "artistName"
    )
    val songEntity: List<SongEntity>
)