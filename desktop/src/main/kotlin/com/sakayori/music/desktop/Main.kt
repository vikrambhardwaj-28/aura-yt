package com.sakayori.music.desktop

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.sakayori.music.desktop.ui.DesktopApp

fun main() = application {
    val windowState = rememberWindowState(
        width = 1200.dp,
        height = 800.dp
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "Aura Music",
        state = windowState
    ) {
        DesktopApp()
    }
}
