package com.example.calculatorapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import android.Manifest
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.util.Log
import android.net.Uri
import android.content.Context
import android.os.Handler
import android.os.Looper

import android.content.ContentUris

import android.media.MediaPlayer
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class AudioFile(
    val id: Long,
    val uri: Uri,
    val title: String,
    val duration: Long,
    val albumId: Long
)

class MediaActivity : ComponentActivity() {
    var audioFiles: List<AudioFile> = emptyList()
    val history = mutableListOf<AudioFile>()
    var Player : MediaPlayer? = null
    lateinit var name: TextView
    lateinit var oblojka: ImageView
    lateinit var back: Button
    lateinit var pause: Button
    lateinit var next: Button
    lateinit var seekBar: SeekBar
    var Ind = -1

    val handler = Handler(Looper.getMainLooper())
    val updateSeekBar = object : Runnable {
        override fun run() {
            Player?.let {
                seekBar.progress = it.currentPosition
                handler.postDelayed(this, 500)
            }
        }
    }

    override fun onRequestPermissionsResult(
        status: Int,
        permissions: Array<String>,
        result: IntArray
    ) {
        super.onRequestPermissionsResult(status, permissions, result)
        if (status == 123 && result.isNotEmpty()
            && result[0] == PackageManager.PERMISSION_GRANTED) {
            audioFiles = getMusicList(this)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        name = findViewById<TextView>(R.id.Nazvanie)
        oblojka = findViewById<ImageView>(R.id.Oblojka)
        back = findViewById<Button>(R.id.bBack)
        pause = findViewById<Button>(R.id.bPause)
        next = findViewById<Button>(R.id.bNext)
        seekBar = findViewById<SeekBar>(R.id.seekBar)



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_MEDIA_AUDIO),
                123
            )
        } else {
            audioFiles = getMusicList(this)
        }
    }

    override fun onResume() {
        super.onResume()

        next.setOnClickListener {
            Next()
        }


        pause.setOnClickListener {
            Player?.let {
                if (it.isPlaying) {
                    it.pause()
                    pause.text = "▶"
                } else {
                    it.start()
                    pause.text = "⏸"
                }
            }
        }

        back.setOnClickListener {
            Back()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    Player?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { }
            override fun onStopTrackingTouch(seekBar: SeekBar?) { }
        })
    }

    fun Next() {
        if (audioFiles.isEmpty()) return

        if (history.isEmpty() || Ind == history.size - 1) {
            val Rindex = (audioFiles.indices).random()
            val track = audioFiles[Rindex]
            history.add(track)
            Ind = history.lastIndex
            Play(track)
        } else {
            Ind += 1
            Play(history[Ind])
        }
    }

    fun Back() {
        if (Ind > 0) {
            Ind -= 1
            Play(history[Ind])
        }
    }
    fun Play(track: AudioFile) {
        Player?.release()
        Player = MediaPlayer().apply {
            setDataSource(this@MediaActivity, track.uri)
            prepare()
            start()
            setOnCompletionListener {
                Next()
            }
        }
        seekBar.max = Player?.duration ?: 0
        handler.post(updateSeekBar)

        name.text = track.title
        val albumArtUri = ContentUris.withAppendedId(
            Uri.parse("content://media/external/audio/albumart"),
            track.albumId
        )
        oblojka.setImageURI(albumArtUri)
        if (oblojka.drawable == null) {
            oblojka.setImageResource(R.drawable.ic_launcher_background)
        }
    }



    fun getMusicList(context: Context): List<AudioFile>{
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val id = MediaStore.Audio.Media._ID
        val title = MediaStore.Audio.Media.TITLE
        val dur = MediaStore.Audio.Media.DURATION
        val albumId = MediaStore.Audio.Media.ALBUM_ID
        val data = arrayOf(id, title, dur, albumId)

        val audio = mutableListOf<AudioFile>()

        val cursor = context.contentResolver.query(uri, data, "${MediaStore.Audio.Media.IS_MUSIC} != 0", null, null)
        cursor?.use {
            while (it.moveToNext()) {
                val audioId = it.getLong(it.getColumnIndexOrThrow(id))
                val audioTitle = it.getString(it.getColumnIndexOrThrow(title)) ?: "Unknown"
                val audioDur = it.getLong(it.getColumnIndexOrThrow(dur))
                val contentUri = ContentUris.withAppendedId(uri, audioId)
                val audioAlbumId = it.getLong(it.getColumnIndexOrThrow(albumId))
                audio.add(AudioFile(audioId, contentUri, audioTitle, audioDur, audioAlbumId))
            }
        }

        return audio
    }
}