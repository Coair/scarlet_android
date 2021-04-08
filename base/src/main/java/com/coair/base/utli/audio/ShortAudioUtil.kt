package com.coair.base.utli.audio

import android.media.MediaPlayer
import com.blankj.utilcode.util.Utils

object ShortAudioUtil {
    private var mediaPlayer: MediaPlayer? = null
    fun releaseMediaPlayer() {
        mediaPlayer?.pause()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun play(id: Int) {
        releaseMediaPlayer()
        mediaPlayer = MediaPlayer.create(Utils.getApp(), id)
        mediaPlayer?.start()
    }
}