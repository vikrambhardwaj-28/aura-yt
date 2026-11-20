package com.sakayori.music.desktop.player

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sakayori.music.desktop.model.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class DesktopAudioPlayer(private val scope: CoroutineScope) {
    var currentSong by mutableStateOf<Song?>(null)
        private set

    var isPlaying by mutableStateOf(false)
        private set

    var currentPositionSeconds by mutableIntStateOf(0)
        private set

    var volume by mutableFloatStateOf(0.8f)
        private set

    var isMuted by mutableStateOf(false)
        private set

    var isShuffle by mutableStateOf(false)
        private set

    var isRepeat by mutableStateOf(false)
        private set

    val queue = mutableStateListOf<Song>()
    private var queueIndex = 0

    private var playbackJob: Job? = null

    fun setQueue(songs: List<Song>, startIndex: Int = 0) {
        queue.clear()
        queue.addAll(songs)
        queueIndex = startIndex.coerceIn(0, (queue.size - 1).coerceAtLeast(0))
        if (queue.isNotEmpty()) {
            playSong(queue[queueIndex])
        }
    }

    fun playSong(song: Song) {
        currentSong = song
        currentPositionSeconds = 0
        isPlaying = true
        startProgressTracker()
    }

    fun togglePlayPause() {
        if (isPlaying) {
            pause()
        } else {
            resume()
        }
    }

    fun pause() {
        isPlaying = false
        playbackJob?.cancel()
    }

    fun resume() {
        if (currentSong != null) {
            isPlaying = true
            startProgressTracker()
        } else if (queue.isNotEmpty()) {
            playSong(queue[queueIndex])
        }
    }

    fun next() {
        if (queue.isEmpty()) return
        queueIndex = if (isShuffle) {
            (0 until queue.size).random()
        } else {
            (queueIndex + 1) % queue.size
        }
        playSong(queue[queueIndex])
    }

    fun previous() {
        if (queue.isEmpty()) return
        if (currentPositionSeconds > 3) {
            seekTo(0)
            return
        }
        queueIndex = if (queueIndex - 1 < 0) queue.size - 1 else queueIndex - 1
        playSong(queue[queueIndex])
    }

    fun seekTo(seconds: Int) {
        val maxDuration = currentSong?.durationSeconds ?: 0
        currentPositionSeconds = seconds.coerceIn(0, maxDuration)
    }

    fun updateVolume(newVolume: Float) {
        volume = newVolume.coerceIn(0f, 1f)
        if (volume > 0f) isMuted = false
    }

    fun toggleMute() {
        isMuted = !isMuted
    }

    fun toggleShuffle() {
        isShuffle = !isShuffle
    }

    fun toggleRepeat() {
        isRepeat = !isRepeat
    }

    private fun startProgressTracker() {
        playbackJob?.cancel()
        playbackJob = scope.launch(Dispatchers.Default) {
            while (isActive && isPlaying) {
                delay(1000)
                val duration = currentSong?.durationSeconds ?: 0
                if (currentPositionSeconds < duration) {
                    currentPositionSeconds++
                } else {
                    if (isRepeat) {
                        currentPositionSeconds = 0
                    } else {
                        launch(Dispatchers.Main) { next() }
                        break
                    }
                }
            }
        }
    }
}
