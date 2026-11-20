package com.sakayori.music.desktop.model

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val durationSeconds: Int,
    val colorHex: Long = 0xFFE50914,
    val streamUrl: String = "",
    val lyrics: List<LyricLine> = emptyList()
) {
    val durationFormatted: String
        get() {
            val minutes = durationSeconds / 60
            val seconds = durationSeconds % 60
            return "%d:%02d".format(minutes, seconds)
        }
}

data class LyricLine(
    val timestampSeconds: Int,
    val text: String
)

data class Playlist(
    val id: String,
    val title: String,
    val songCount: Int,
    val colorHex: Long
)

enum class NavigationTab(val label: String) {
    HOME("Home"),
    EXPLORE("Explore"),
    LIBRARY("Library"),
    SEARCH("Search"),
    LYRICS("Lyrics"),
    SETTINGS("Settings")
}
