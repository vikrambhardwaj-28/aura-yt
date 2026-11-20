package com.sakayori.music.desktop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sakayori.music.desktop.model.NavigationTab
import com.sakayori.music.desktop.model.Song

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainContent(
    currentTab: NavigationTab,
    searchQuery: String,
    featuredSongs: List<Song>,
    trendingSongs: List<Song>,
    onSongPlay: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AuraBackground)
            .padding(horizontal = 32.dp, vertical = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (searchQuery.isNotEmpty()) {
            // Search Results Table
            val results = (featuredSongs + trendingSongs).filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.artist.contains(searchQuery, ignoreCase = true) ||
                        it.album.contains(searchQuery, ignoreCase = true)
            }

            Text(
                text = "Search results for \"$searchQuery\"",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (results.isEmpty()) {
                Text(
                    text = "No songs found matching \"$searchQuery\"",
                    color = AuraTextMuted,
                    fontSize = 14.sp
                )
            } else {
                results.forEachIndexed { index, song ->
                    SearchTrackRow(
                        index = index + 1,
                        song = song,
                        onPlay = { onSongPlay(song) }
                    )
                }
            }
        } else {
            // Standard Desktop Dashboard (Designed for 13"+ MacBook)
            Text(
                text = "Welcome to Aura Music",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Your high-fidelity YouTube Music experience on macOS",
                fontSize = 13.sp,
                color = AuraTextSecondary
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Section 1: Quick Picks (Responsive Grid of 6 Cards)
            Text(
                text = "Quick Picks",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(14.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                featuredSongs.take(6).forEach { song ->
                    QuickPickCard(
                        song = song,
                        onPlay = { onSongPlay(song) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Section 2: Trending & Recommended (Album Cards Grid)
            Text(
                text = "Trending on YouTube Music",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(14.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                trendingSongs.forEach { song ->
                    TrendingSongCard(
                        song = song,
                        onPlay = { onSongPlay(song) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
private fun QuickPickCard(song: Song, onPlay: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(280.dp)
            .height(64.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(AuraCard)
            .clickable(onClick = onPlay)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color(song.colorHex)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.GraphicEq,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = song.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                maxLines = 1
            )
            Text(
                text = song.artist,
                fontSize = 11.sp,
                color = AuraTextSecondary,
                maxLines = 1
            )
        }
        Box(
            modifier = Modifier
                .padding(end = 12.dp)
                .size(32.dp)
                .clip(CircleShape)
                .background(AuraAccent),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun TrendingSongCard(song: Song, onPlay: () -> Unit) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(AuraCard)
            .clickable(onClick = onPlay)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(136.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(song.colorHex)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.GraphicEq,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.size(42.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = song.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = song.artist,
            fontSize = 12.sp,
            color = AuraTextSecondary,
            maxLines = 1
        )
    }
}

@Composable
private fun SearchTrackRow(
    index: Int,
    song: Song,
    onPlay: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onPlay)
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Text(
            text = index.toString(),
            fontSize = 13.sp,
            color = AuraTextMuted,
            modifier = Modifier.width(32.dp)
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color(song.colorHex)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.GraphicEq,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = song.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(
                text = song.artist,
                fontSize = 12.sp,
                color = AuraTextSecondary
            )
        }
        Text(
            text = song.album,
            fontSize = 13.sp,
            color = AuraTextSecondary,
            modifier = Modifier.weight(0.7f)
        )
        Text(
            text = song.durationFormatted,
            fontSize = 12.sp,
            color = AuraTextMuted
        )
    }
    Divider(color = AuraBorder.copy(alpha = 0.5f), thickness = 0.5.dp)
}
