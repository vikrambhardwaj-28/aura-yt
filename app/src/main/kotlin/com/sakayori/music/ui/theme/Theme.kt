/**
 * Sakayori Music Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.sakayori.music.ui.theme

import android.graphics.Bitmap
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.luminance
import com.sakayori.music.constants.CustomAppBackgroundColorKey
import com.sakayori.music.constants.CustomAppTextColorKey
import com.sakayori.music.utils.rememberPreference
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.rememberDynamicColorScheme
import com.materialkolor.score.Score

val DefaultThemeColor = Color(0xFFFA2D48) // Apple Music signature crimson/red accent

@Composable
fun SakayoriTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    pureBlack: Boolean = false,
    themeColor: Color = DefaultThemeColor,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val customBgColorInt by rememberPreference(CustomAppBackgroundColorKey, defaultValue = 0)
    val customTextColorInt by rememberPreference(CustomAppTextColorKey, defaultValue = 0)

    // Determine if system dynamic colors should be used (Android S+ and default theme color)
    val useSystemDynamicColor = (themeColor == DefaultThemeColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)

    // Select the appropriate color scheme generation method
    val baseColorScheme = if (useSystemDynamicColor) {
        // Use standard Material 3 dynamic color functions for system wallpaper colors
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        // Use materialKolor only when a specific seed color is provided
        rememberDynamicColorScheme(
            seedColor = themeColor, // themeColor is guaranteed non-default here
            isDark = darkTheme,
            specVersion = ColorSpec.SpecVersion.SPEC_2025,
            style = PaletteStyle.TonalSpot // Keep existing style
        )
    }

    // Apply pureBlack or custom modifications if needed
    val colorScheme = remember(baseColorScheme, pureBlack, darkTheme, customBgColorInt, customTextColorInt) {
        val base = if (darkTheme && pureBlack) {
            baseColorScheme.pureBlack(true)
        } else {
            baseColorScheme
        }
        var updated = base
        if (customBgColorInt != 0) {
            val customColor = Color(customBgColorInt)
            updated = updated.copy(
                background = customColor,
                surface = customColor,
                surfaceContainer = customColor.copy(alpha = 0.95f),
                surfaceContainerHigh = customColor.copy(alpha = 0.9f),
                surfaceContainerHighest = customColor.copy(alpha = 0.85f),
                surfaceVariant = customColor.copy(alpha = 0.9f)
            )
        }
        if (customTextColorInt != 0) {
            val customText = Color(customTextColorInt)
            updated = updated.copy(
                onBackground = customText,
                onSurface = customText,
                onSurfaceVariant = customText.copy(alpha = 0.75f)
            )
        }
        updated
    }

    // Use standard MaterialTheme instead of MaterialExpressiveTheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // Use the defined AppTypography
        content = content
    )
}

fun Bitmap.extractThemeColor(): Color {
    val colorsToPopulation = Palette.from(this)
        .maximumColorCount(8)
        .generate()
        .swatches
        .associate { it.rgb to it.population }
    val rankedColors = Score.score(colorsToPopulation)
    return Color(rankedColors.first())
}

fun Bitmap.extractGradientColors(): List<Color> {
    val extractedColors = Palette.from(this)
        .maximumColorCount(64)
        .generate()
        .swatches
        .associate { it.rgb to it.population }

    val orderedColors = Score.score(extractedColors, 2, 0xff4285f4.toInt(), true)
        .sortedByDescending { Color(it).luminance() }

    return if (orderedColors.size >= 2)
        listOf(Color(orderedColors[0]), Color(orderedColors[1]))
    else
        listOf(Color(0xFF595959), Color(0xFF0D0D0D))
}

fun ColorScheme.pureBlack(apply: Boolean) =
    if (apply) copy(
        surface = Color.Black,
        background = Color.Black
    ) else this

val ColorSaver = object : Saver<Color, Int> {
    override fun restore(value: Int): Color = Color(value)
    override fun SaverScope.save(value: Color): Int = value.toArgb()
}

/**
 * Ultra-premium 3D Glossy Apple Music Glass effect modifier.
 */
@Composable
fun Modifier.glassBackground(
    color: Color = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.7f),
    shape: Shape = MaterialTheme.shapes.medium
): Modifier = this
    .background(color, shape)
    // 3D Top Specular Light Reflection (Apple Music glossy shine)
    .background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.32f),
                Color.White.copy(alpha = 0.08f),
                Color.Transparent,
                Color.Black.copy(alpha = 0.06f)
            )
        ),
        shape = shape
    )
    // Premium 3D Bevel Border Highlight
    .border(
        width = 1.dp,
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.7f),
                Color.White.copy(alpha = 0.2f),
                Color.White.copy(alpha = 0.05f)
            )
        ),
        shape = shape
    )
    // 3D glossy border highlight
    .border(
        width = 1.dp,
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.45f),
                Color.White.copy(alpha = 0.12f),
                Color.Transparent
            )
        ),
        shape = shape
    )
