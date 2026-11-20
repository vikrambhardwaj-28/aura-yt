/**
 * Sakayori Music Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.sakayori.music.ui.screens

import androidx.compose.animation.core.*

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sakayori.music.R
import kotlinx.coroutines.delay

data class OnboardingSlide(
    val bgRes: Int,
    val subtitle: String,
    val titleLine1: String,
    val titleHighlight: String,
    val titleLine2: String
)

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    val slides = listOf(
        OnboardingSlide(
            bgRes = R.drawable.img1,
            subtitle = "AURA MUSIC",
            titleLine1 = "Listen to ",
            titleHighlight = "the Best Music",
            titleLine2 = " Everyday"
        ),
        OnboardingSlide(
            bgRes = R.drawable.img2,
            subtitle = "DISCOVER",
            titleLine1 = "Explore ",
            titleHighlight = "Global Charts",
            titleLine2 = " & Playlists"
        ),
        OnboardingSlide(
            bgRes = R.drawable.img3,
            subtitle = "EXPERIENCE",
            titleLine1 = "Pure Glossy ",
            titleHighlight = "3D Glass",
            titleLine2 = " Design"
        )
    )

    var currentSlide by remember { mutableStateOf(0) }
    val slideDuration = 4000L // 4 seconds per slide

    LaunchedEffect(currentSlide) {
        delay(slideDuration)
        if (currentSlide < slides.size - 1) {
            currentSlide++
        } else {
            currentSlide = 0
        }
    }

    val slide = slides[currentSlide]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF07070B)),
        contentAlignment = Alignment.Center
    ) {
        // Background: crystal clear at top, fading smoothly into dark at the bottom
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            val bgBrush = when (currentSlide) {
                0 -> Brush.radialGradient(
                    colors = listOf(Color(0xFFE91E63).copy(alpha = 0.35f), Color(0xFF07070B)),
                    radius = 1200f
                )
                1 -> Brush.radialGradient(
                    colors = listOf(Color(0xFF3F51B5).copy(alpha = 0.35f), Color(0xFF07070B)),
                    radius = 1200f
                )
                else -> Brush.radialGradient(
                    colors = listOf(Color(0xFF9C27B0).copy(alpha = 0.35f), Color(0xFF07070B)),
                    radius = 1200f
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgBrush)
            )

            // Vertical gradient overlay: transparent at top (clear), fading to dark at bottom
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xFF07070B).copy(alpha = 0.3f),
                                Color(0xFF07070B).copy(alpha = 0.8f),
                                Color(0xFF07070B)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Progress Bars (3 segments)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                slides.forEachIndexed { index, _ ->
                    val progress by animateFloatAsState(
                        targetValue = if (index < currentSlide) 1f else if (index == currentSlide) 1f else 0f,
                        animationSpec = if (index == currentSlide) tween(durationMillis = slideDuration.toInt(), easing = LinearEasing) else tween(0),
                        label = "progress_$index"
                    )
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = Color(0xFFFA2D48),
                        trackColor = Color.White.copy(alpha = 0.2f),
                    )
                }
            }

            // Center Title
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = slide.subtitle,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFFFA2D48),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = buildAnnotatedString {
                        append(slide.titleLine1)
                        withStyle(androidx.compose.ui.text.SpanStyle(color = Color(0xFFFA2D48))) {
                            append(slide.titleHighlight)
                        }
                        append(slide.titleLine2)
                    },
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 42.sp,
                        color = Color.White,
                        lineHeight = 50.sp
                    )
                )
            }

            // Bottom Section with Gradient Button & Version
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Let's Get Started Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(elevation = 16.dp, shape = RoundedCornerShape(28.dp), spotColor = Color(0xFFD81B60).copy(alpha = 0.6f))
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFE91E63),
                                    Color(0xFF9C27B0)
                                )
                            )
                        )
                        .clickable { onSplashFinished() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Let's Get Started",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Version 1.2.1",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.4f)
                )
            }
        }
    }
}
