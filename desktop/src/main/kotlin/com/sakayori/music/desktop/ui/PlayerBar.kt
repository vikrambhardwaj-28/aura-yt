package com.sakayori.music.desktop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sakayori.music.desktop.player.DesktopAudioPlayer

@Composable
fun PlayerBar(
    player: DesktopAudioPlayer,
    isLyricsOpen: Boolean,
    onToggleLyrics: () -> Unit,
    modifier: Modifier = Modifier
) {
    val song = player.currentSong
    var isLiked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(88.dp)
            .background(AuraSurface)
    ) {
        Divider(color = AuraBorder, thickness = 1.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp)
        ) {
            // Left Section: Track Metadata & Album Art
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.width(280.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (song != null) Color(song.colorHex) else AuraCard),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.GraphicEq,
                        contentDescription = "Cover",
                        tint = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = song?.title ?: "Select a track to play",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = song?.artist ?: "Aura Music",
                        fontSize = 12.sp,
                        color = AuraTextSecondary,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { isLiked = !isLiked }) {
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (isLiked) AuraAccent else AuraTextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Center Section: Playback Controls & Timeline Slider
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                // Controls Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = { player.toggleShuffle() }) {
                        Icon(
                            imageVector = Icons.Default.Shuffle,
                            contentDescription = "Shuffle",
                            tint = if (player.isShuffle) AuraAccent else AuraTextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    IconButton(onClick = { player.previous() }) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Previous",
                            tint = AuraTextSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Circular Play/Pause Button
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .clickable { player.togglePlayPause() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (player.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (player.isPlaying) "Pause" else "Play",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { player.next() }) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Next",
                            tint = AuraTextSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(onClick = { player.toggleRepeat() }) {
                        Icon(
                            imageVector = Icons.Default.Repeat,
                            contentDescription = "Repeat",
                            tint = if (player.isRepeat) AuraAccent else AuraTextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Timeline Seekbar Row
                val duration = song?.durationSeconds ?: 0
                val progress = player.currentPositionSeconds

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.width(460.dp)
                ) {
                    Text(
                        text = formatTime(progress),
                        fontSize = 11.sp,
                        color = AuraTextMuted
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Slider(
                        value = if (duration > 0) progress.toFloat() / duration else 0f,
                        onValueChange = { frac ->
                            player.seekTo((frac * duration).toInt())
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color.White,
                            inactiveTrackColor = AuraBorder
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = song?.durationFormatted ?: "0:00",
                        fontSize = 11.sp,
                        color = AuraTextMuted
                    )
                }
            }

            // Right Section: Lyrics, Queue & Volume Slider
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.width(280.dp)
            ) {
                IconButton(onClick = onToggleLyrics) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Lyrics",
                        tint = if (isLyricsOpen) AuraAccent else AuraTextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.QueueMusic,
                        contentDescription = "Queue",
                        tint = AuraTextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                IconButton(onClick = { player.toggleMute() }) {
                    Icon(
                        imageVector = if (player.isMuted || player.volume == 0f) Icons.Default.VolumeMute else Icons.Default.VolumeUp,
                        contentDescription = "Volume",
                        tint = AuraTextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Slider(
                    value = if (player.isMuted) 0f else player.volume,
                    onValueChange = { player.updateVolume(it) },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color.White,
                        inactiveTrackColor = AuraBorder
                    ),
                    modifier = Modifier.width(90.dp)
                )
            }
        }
    }
}

private fun formatTime(totalSeconds: Int): String {
    val m = totalSeconds / 60
    val s = totalSeconds % 60
    return "%d:%02d".format(m, s)
}
