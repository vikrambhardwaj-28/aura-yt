package com.sakayori.music.desktop.ui

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val AuraBackground = Color(0xFF0F0F0F)
val AuraSurface = Color(0xFF1A1A1A)
val AuraCard = Color(0xFF242424)
val AuraCardHover = Color(0xFF303030)
val AuraAccent = Color(0xFFFF0033)
val AuraAccentGlow = Color(0x33FF0033)
val AuraTextPrimary = Color(0xFFFFFFFF)
val AuraTextSecondary = Color(0xFFAAAAAA)
val AuraTextMuted = Color(0xFF6E6E6E)
val AuraBorder = Color(0xFF2B2B2B)

val AuraDarkColorScheme: ColorScheme = darkColorScheme(
    primary = AuraAccent,
    background = AuraBackground,
    surface = AuraSurface,
    surfaceVariant = AuraCard,
    onPrimary = Color.White,
    onBackground = AuraTextPrimary,
    onSurface = AuraTextPrimary,
    onSurfaceVariant = AuraTextSecondary
)

@Composable
fun AuraTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AuraDarkColorScheme,
        content = content
    )
}
