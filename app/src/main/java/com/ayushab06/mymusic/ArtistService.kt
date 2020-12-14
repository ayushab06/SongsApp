package com.ayushab06.mymusic

import com.ayushab06.mymusic.model.ArtistData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistService {
    @GET("search")
    fun getSongs(@Query("term")artistName:String):Call<ArtistData>
}