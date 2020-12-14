package com.ayushab06.mymusic

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayushab06.mymusic.entities.SongEntity

class SongsAdapter(private val context: Context, private val songs: List<SongEntity>):
    RecyclerView.Adapter<SongsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.single_song,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        holder.itemView.setOnClickListener {
            val intent =Intent(Intent.ACTION_VIEW,Uri.parse(songs[position].trackViewUrl))
            context.startActivity(intent)

        }
    }

    override fun getItemCount()=songs.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvSongName=view.findViewById<TextView>(R.id.tvSongName)
        fun bind(position: Int) {
            tvSongName.text=songs[position].trackName

        }

    }

}
