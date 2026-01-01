plugins {
    alias(libs.plugins.finanzas.android.library)
    alias(libs.plugins.finanzas.android.library.compose)
    alias(libs.plugins.finanzas.android.hilt)
}

android {
    namespace = "com.soleel.finanzas.feature.launch"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.ui)

    implementation(projects.data.preferences)

    implementation(projects.feature.configuration)
    implementation(projects.feature.home)
}