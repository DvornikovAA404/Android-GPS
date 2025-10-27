package com.example.calculatorapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import android.net.Uri
import android.content.ContentUris
import android.provider.MediaStore

class AudioFile(
    val id: Long,
    val uri: Uri,
    val title: String,
    val duration: Long,
    val albumId: Long
)
class TrackListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_list)

        val trackIds = intent.getLongArrayExtra("ids") ?: longArrayOf()
        val trackTitles = intent.getStringArrayExtra("titles") ?: emptyArray()
        val trackDurations = intent.getLongArrayExtra("durations") ?: longArrayOf()
        val trackAlbumIds = intent.getLongArrayExtra("albumIds") ?: longArrayOf()

        val tracks = mutableListOf<AudioFile>()
        for (i in trackTitles.indices) {
            if (i < trackIds.size && i < trackDurations.size && i < trackAlbumIds.size) {
                val uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    trackIds[i]
                )
                tracks.add(
                    AudioFile(
                        id = trackIds[i],
                        uri = uri,
                        title = trackTitles[i],
                        duration = trackDurations[i],
                        albumId = trackAlbumIds[i]
                    )
                )
            }
        }

        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = TrackAdapter(tracks) { track ->
            val resultIntent = Intent(this, MediaActivity::class.java)
            resultIntent.putExtra("selectedTrackId", track.id)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}

class TrackAdapter(
    val tracks: List<AudioFile>,
    val onClick: (AudioFile) -> Unit
) : RecyclerView.Adapter<TrackAdapter.SH>() {

    class SH(v: android.view.View) : ViewHolder(v) {
        val txt = v.findViewById<TextView>(android.R.id.text1)
    }

    override fun onCreateViewHolder(p: ViewGroup, vt: Int): SH {
        val v = LayoutInflater.from(p.context)
            .inflate(android.R.layout.simple_list_item_1, p, false)
        return SH(v)
    }

    override fun onBindViewHolder(h: SH, pos: Int) {
        h.txt.text = tracks[pos].title
        h.itemView.setOnClickListener { onClick(tracks[pos]) }
    }

    override fun getItemCount() = tracks.size
}
