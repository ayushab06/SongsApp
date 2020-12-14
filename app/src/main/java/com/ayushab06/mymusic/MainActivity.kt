package com.ayushab06.mymusic

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.ayushab06.mymusic.Database.ArtistDatabase
import com.ayushab06.mymusic.entities.ArtistEntity
import com.ayushab06.mymusic.entities.Relations.ArtistWithSongs
import com.ayushab06.mymusic.entities.SongEntity
import com.ayushab06.mymusic.model.ArtistData
import com.ayushab06.mymusic.util.ConnectionManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.withTestContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://itunes.apple.com/"

class MainActivity : CoJob() {
    lateinit var songs: List<SongEntity>
    lateinit var db: ArtistDatabase
    lateinit var artist: String
    val context = this@MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(this, ArtistDatabase::class.java, "artist-data").build()
        rvSongs.layoutManager = LinearLayoutManager(this)

        svArtistName.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                artist = query!!
                getData(artist)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }

    private fun getData(artist: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val artistService = retrofit.create(ArtistService::class.java)
        if (ConnectionManager().checkConnectivity(this))
            saveToDataBase(artistService, artist)
        else
            getOfflineData(artist)

    }

    private fun getOfflineData(artist: String) {
        GlobalScope.launch {
            context.let {
                it
                val AllSongs = db.artistDao().getArtistWithSongs(artist)

                if (AllSongs.isEmpty())
                    NodataLayout.visibility=View.VISIBLE
                else {
                    songs = AllSongs[0].songEntity
                    rvSongs.adapter = SongsAdapter(context, songs)
                }

            }
        }

    }

    private fun saveToDataBase(artistService: ArtistService, artist: String) {
        artistService.getSongs(artist).enqueue(object : Callback<ArtistData> {
            override fun onResponse(call: Call<ArtistData>, response: Response<ArtistData>) {
                val body = response.body()
                val artistEntity = ArtistEntity(artist)
                if (body?.results?.isEmpty() == true)
                    NodataLayout.visibility=View.VISIBLE
                else {

                    songs = body?.results!!
                    songs?.forEach { it.artistName = artist.toLowerCase() }
                    GlobalScope.launch {
                        var artistEntity = ArtistEntity(artist)

                        db.artistDao().insertArtist(artistEntity)

                        for (a in songs) {
                            db.artistDao().insertSongs(a)
                        }
                    }
                    rvSongs.adapter = SongsAdapter(this@MainActivity, songs)

                }
            }

            override fun onFailure(call: Call<ArtistData>, t: Throwable) {
                Log.e("Tag", "an error occurred $t")
            }
        })

    }


}

