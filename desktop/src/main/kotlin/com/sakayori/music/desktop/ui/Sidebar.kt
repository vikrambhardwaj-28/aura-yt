package com.sakayori.music.desktop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sakayori.music.desktop.model.NavigationTab
import com.sakayori.music.desktop.model.Playlist

@Composable
fun Sidebar(
    currentTab: NavigationTab,
    playlists: List<Playlist>,
    onTabSelected: (NavigationTab) -> Unit,
    onPlaylistSelected: (Playlist) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(240.dp)
            .fillMaxHeight()
            .background(AuraSurface)
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // App Logo & Brand Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(AuraAccent),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.GraphicEq,
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Aura Music",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "macOS Desktop",
                    fontSize = 11.sp,
                    color = AuraTextMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Discover Section
        SectionHeader("DISCOVER")
        SidebarItem(
            icon = Icons.Default.Home,
            label = "Home",
            selected = currentTab == NavigationTab.HOME,
            onClick = { onTabSelected(NavigationTab.HOME) }
        )
        SidebarItem(
            icon = Icons.Default.Explore,
            label = "Explore",
            selected = currentTab == NavigationTab.EXPLORE,
            onClick = { onTabSelected(NavigationTab.EXPLORE) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Library Section
        SectionHeader("MY LIBRARY")
        SidebarItem(
            icon = Icons.Default.LibraryMusic,
            label = "Library",
            selected = currentTab == NavigationTab.LIBRARY,
            onClick = { onTabSelected(NavigationTab.LIBRARY) }
        )
        SidebarItem(
            icon = Icons.Default.Favorite,
            label = "Liked Songs",
            selected = false,
            onClick = { onTabSelected(NavigationTab.LIBRARY) }
        )
        SidebarItem(
            icon = Icons.Default.Album,
            label = "Albums",
            selected = false,
            onClick = { onTabSelected(NavigationTab.LIBRARY) }
        )
        SidebarItem(
            icon = Icons.Default.Person,
            label = "Artists",
            selected = false,
            onClick = { onTabSelected(NavigationTab.LIBRARY) }
        )

        Spacer(modifier = Modifier.height(24.dp))
        Divider(color = AuraBorder, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        // Playlists Section
        SectionHeader("PLAYLISTS")
        playlists.forEach { playlist ->
            PlaylistItem(playlist = playlist, onClick = { onPlaylistSelected(playlist) })
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = AuraTextMuted,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        letterSpacing = 1.sp
    )
}

@Composable
private fun SidebarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bgModifier = if (selected) {
        Modifier.background(AuraCard, RoundedCornerShape(8.dp))
    } else {
        Modifier
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .then(bgModifier)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) AuraAccent else AuraTextSecondary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) Color.White else AuraTextSecondary
        )
    }
}

@Composable
private fun PlaylistItem(playlist: Playlist, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(playlist.colorHex))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = playlist.title,
            fontSize = 13.sp,
            color = AuraTextSecondary,
            maxLines = 1
        )
    }
}
