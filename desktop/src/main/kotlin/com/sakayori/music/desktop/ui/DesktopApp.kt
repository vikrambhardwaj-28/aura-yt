package com.sakayori.music.desktop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sakayori.music.desktop.model.LyricLine
import com.sakayori.music.desktop.model.NavigationTab
import com.sakayori.music.desktop.model.Playlist
import com.sakayori.music.desktop.model.Song
import com.sakayori.music.desktop.player.DesktopAudioPlayer

@Composable
fun DesktopApp() {
    val scope = rememberCoroutineScope()
    val player = remember { DesktopAudioPlayer(scope) }

    var currentTab by remember { mutableStateOf(NavigationTab.HOME) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    var isLyricsOpen by remember { mutableStateOf(false) }

    // Sample catalogue of curated YouTube Music tracks with synced lyrics
    val sampleSongs = remember {
        listOf(
            Song(
                id = "1",
                title = "Starboy",
                artist = "The Weeknd ft. Daft Punk",
                album = "Starboy",
                durationSeconds = 230,
                colorHex = 0xFF8A2BE2,
                lyrics = listOf(
                    LyricLine(0, "I'm tryna put you in the worst mood, ah"),
                    LyricLine(4, "P1 cleaner than your church shoes, ah"),
                    LyricLine(8, "Milli point two just to hurt you, ah"),
                    LyricLine(12, "All red Lamb' just to tease you, ah"),
                    LyricLine(16, "None of these toys on lease too, ah"),
                    LyricLine(20, "Look what you've done"),
                    LyricLine(24, "I'm a motherfuckin' starboy!")
                )
            ),
            Song(
                id = "2",
                title = "Blinding Lights",
                artist = "The Weeknd",
                album = "After Hours",
                durationSeconds = 200,
                colorHex = 0xFFE50914,
                lyrics = listOf(
                    LyricLine(0, "Yeah"),
                    LyricLine(6, "I've been tryna call"),
                    LyricLine(10, "I've been on my own for long enough"),
                    LyricLine(16, "Maybe you can show me how to love, maybe"),
                    LyricLine(24, "I'm going through withdrawals"),
                    LyricLine(30, "You don't even have to do too much"),
                    LyricLine(36, "I said, ooh, I'm blinded by the lights!")
                )
            ),
            Song(
                id = "3",
                title = "Levitating",
                artist = "Dua Lipa",
                album = "Future Nostalgia",
                durationSeconds = 203,
                colorHex = 0xFF20B2AA,
                lyrics = listOf(
                    LyricLine(0, "If you wanna run away with me, I know a galaxy"),
                    LyricLine(5, "And I can take you for a ride"),
                    LyricLine(9, "I had a premonition that we fell into a rhythm"),
                    LyricLine(14, "Where the music don't stop for life"),
                    LyricLine(18, "Glitter in the sky, glitter in my eyes"),
                    LyricLine(22, "Shining just the way I like"),
                    LyricLine(26, "You want me, I want you, baby")
                )
            ),
            Song(
                id = "4",
                title = "Save Your Tears",
                artist = "The Weeknd",
                album = "After Hours",
                durationSeconds = 215,
                colorHex = 0xFFFF4500
            ),
            Song(
                id = "5",
                title = "As It Was",
                artist = "Harry Styles",
                album = "Harry's House",
                durationSeconds = 167,
                colorHex = 0xFF4169E1
            ),
            Song(
                id = "6",
                title = "Stay",
                artist = "The Kid LAROI & Justin Bieber",
                album = "F*CK LOVE 3",
                durationSeconds = 141,
                colorHex = 0xFFFF1493
            ),
            Song(
                id = "7",
                title = "Bad Guy",
                artist = "Billie Eilish",
                album = "WHEN WE ALL FALL ASLEEP",
                durationSeconds = 194,
                colorHex = 0xFF32CD32
            ),
            Song(
                id = "8",
                title = "Shape of You",
                artist = "Ed Sheeran",
                album = "÷ (Divide)",
                durationSeconds = 233,
                colorHex = 0xFF00CED1
            )
        )
    }

    val samplePlaylists = remember {
        listOf(
            Playlist("p1", "🔥 Top Hits 2026", 50, 0xFFFF0033),
            Playlist("p2", "🌙 Chill Lofi Beats", 34, 0xFF8A2BE2),
            Playlist("p3", "⚡ Workout Energy", 42, 0xFFFF8C00),
            Playlist("p4", "🌧️ Rainy Day Acoustic", 28, 0xFF4682B4)
        )
    }

    AuraTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AuraBackground)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Top Row: Sidebar + Main Content & TopBar
                Row(modifier = Modifier.weight(1f)) {
                    // Left Navigation Sidebar
                    Sidebar(
                        currentTab = currentTab,
                        playlists = samplePlaylists,
                        onTabSelected = { currentTab = it },
                        onPlaylistSelected = { playlist ->
                            // Load playlist tracks
                            player.setQueue(sampleSongs)
                        }
                    )

                    // Right Area: TopBar + Scrollable Content
                    Column(modifier = Modifier.weight(1f)) {
                        TopBar(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { searchQuery = it },
                            selectedFilter = selectedFilter,
                            onFilterSelected = { selectedFilter = it },
                            onSettingsClick = { currentTab = NavigationTab.SETTINGS }
                        )

                        MainContent(
                            currentTab = currentTab,
                            searchQuery = searchQuery,
                            featuredSongs = sampleSongs,
                            trendingSongs = sampleSongs.reversed(),
                            onSongPlay = { song ->
                                player.setQueue(sampleSongs, sampleSongs.indexOf(song))
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Bottom Persistent Music Player Bar
                PlayerBar(
                    player = player,
                    isLyricsOpen = isLyricsOpen,
                    onToggleLyrics = { isLyricsOpen = !isLyricsOpen }
                )
            }

            // Synced Lyrics Overlay Panel (When toggled)
            if (isLyricsOpen) {
                LyricsView(
                    song = player.currentSong,
                    currentPositionSeconds = player.currentPositionSeconds,
                    onSeekTo = { player.seekTo(it) },
                    onClose = { isLyricsOpen = false }
                )
            }
        }
    }
}
