plugins {
    alias(libs.plugins.finanzas.android.library)
    alias(libs.plugins.finanzas.android.library.compose)
    alias(libs.plugins.finanzas.android.hilt)
}

android {
    namespace = "com.soleel.finanzas.feature.stats"
}

dependencies {
    api(projects.core.common)
    api(projects.domain.stats)
    api(projects.domain.visualtransformation)
}