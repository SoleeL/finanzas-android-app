plugins {
    alias(libs.plugins.finanzas.android.library)
    alias(libs.plugins.finanzas.android.library.compose)
}

android {
    namespace = "com.soleel.finanzas.core.ui"
}

dependencies {
    api(projects.core.common)
}