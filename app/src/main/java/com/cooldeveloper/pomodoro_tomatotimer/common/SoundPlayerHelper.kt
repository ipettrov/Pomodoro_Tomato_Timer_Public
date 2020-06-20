package com.cooldeveloper.pomodoro_tomatotimer.common

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class SoundPlayerHelper {

    companion object {
        private lateinit var mediaPlayer: MediaPlayer

        fun playSound(context: Context?, sound: Int) {
            mediaPlayer = MediaPlayer.create(context, sound)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                mediaPlayer.release()
            }
        }
    }
}