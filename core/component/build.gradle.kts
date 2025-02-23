plugins {
    alias(libs.plugins.finanzas.android.library)
    alias(libs.plugins.finanzas.android.library.compose)
}

android {
    namespace = "com.soleel.finanzas.core.component"
}

dependencies {
    api(projects.core.common)
    api(projects.core.ui)
}