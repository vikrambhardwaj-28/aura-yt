/**
 * Sakayori Music Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.sakayori.music.ui.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sakayori.music.BuildConfig
import com.sakayori.music.LocalPlayerAwareWindowInsets
import com.sakayori.music.R
import com.sakayori.music.ui.component.IconButton
import com.sakayori.music.ui.component.Material3SettingsGroup
import com.sakayori.music.ui.component.Material3SettingsItem
import com.sakayori.music.ui.utils.backToMain

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AboutScreen(
    navController: NavController,
) {
    val uriHandler = LocalUriHandler.current
    val windowInsets = LocalPlayerAwareWindowInsets.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(windowInsets.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(
            Modifier.windowInsetsPadding(
                windowInsets.only(WindowInsetsSides.Top)
            )
        )

        Spacer(Modifier.height(16.dp))

        // App Header Section
        Card(
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.22f))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(R.drawable.ic_logo_oval),
                        contentDescription = null,
                        modifier = Modifier.size(84.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.about_icon),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier.size(64.dp)
                    )
                }
        
                Spacer(Modifier.width(20.dp))
        
                Column {
                    Text(
                        text = "Aura Music",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface,
                        letterSpacing = (-0.5).sp
                    )
            
                    Spacer(Modifier.height(8.dp))
            
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = BuildConfig.VERSION_NAME,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = "Made in India",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Developer Info Card
        Card(
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.22f))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
            ) {
                Text(
                    text = "Vikram Bhardwaj",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Lead Developer & Creator",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Modinagar Ghaziabad uttar pradesh india",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Community & Social Links
        Material3SettingsGroup(
            title = "Community & Info",
            items = listOf(
                Material3SettingsItem(
                    icon = painterResource(R.drawable.instagram),
                    title = { Text(text = "Instagram", fontWeight = FontWeight.SemiBold) },
                    description = { Text("@vikrm_bhardwaj") },
                    onClick = { uriHandler.openUri("https://www.instagram.com/vikrm_bhardwaj?igsh=OWh4ZHprbW5rNTZv") }
                ),
                Material3SettingsItem(
                    icon = painterResource(R.drawable.share),
                    title = { Text(text = "Facebook", fontWeight = FontWeight.SemiBold) },
                    description = { Text("Vikram Bhardwaj") },
                    onClick = { uriHandler.openUri("https://www.facebook.com/share/196jZuggxg/") }
                ),
                Material3SettingsItem(
                    icon = painterResource(R.drawable.link),
                    title = { Text(text = "Desktop & Web Experience", fontWeight = FontWeight.SemiBold) },
                    description = { Text("aura-music.markiv.site") },
                    onClick = { uriHandler.openUri("https://aura-music.markiv.site") }
                ),
                Material3SettingsItem(
                    icon = painterResource(R.drawable.info),
                    title = { Text(text = "Email", fontWeight = FontWeight.SemiBold) },
                    description = { Text("vikram.2872006@gmail.com") },
                    onClick = { uriHandler.openUri("mailto:vikram.2872006@gmail.com") }
                ),
                Material3SettingsItem(
                    icon = painterResource(R.drawable.bug_report),
                    title = { Text(text = "Send Feedback", fontWeight = FontWeight.SemiBold) },
                    description = { Text("Share your feedback or report issues") },
                    onClick = { uriHandler.openUri("mailto:vikram.2872006@gmail.com?subject=Aura%20Music%20Feedback") }
                )
            )
        )

        Spacer(Modifier.height(48.dp))
    }

    TopAppBar(
        title = { Text(stringResource(R.string.about)) },
        navigationIcon = {
            IconButton(
                onClick = navController::navigateUp,
                onLongClick = navController::backToMain,
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = stringResource(R.string.cd_back),
                )
            }
        }
    )
}
