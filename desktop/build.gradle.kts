plugins {
    kotlin("jvm")
    alias(libs.plugins.compose.compiler)
    id("org.jetbrains.compose") version "1.7.0"
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("io.ktor:ktor-client-core:2.3.12")
    implementation("io.ktor:ktor-client-cio:2.3.12")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.12")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.12")
    implementation("javazoom:jlayer:1.0.1")
}

kotlin {
    jvmToolchain(21)
}

compose.desktop {
    application {
        mainClass = "com.sakayori.music.desktop.MainKt"

        nativeDistributions {
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg)
            packageName = "AuraMusic"
            packageVersion = "1.0.0"
            description = "Aura Music - YouTube Music Client for macOS"
            copyright = "© 2026 Aura Music"
            vendor = "Aura Music"

            macOS {
                bundleID = "com.vikrambhardwaj.auramusic"
                dockName = "Aura Music"
                packageBuildVersion = "1"
            }
        }
    }
}
