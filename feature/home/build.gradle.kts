plugins {
    alias(libs.plugins.finanzas.android.library)
    alias(libs.plugins.finanzas.android.library.compose)
    alias(libs.plugins.finanzas.android.hilt)
}

android {
    namespace = "com.soleel.finanzas.feature.home"
}

dependencies {
    api(projects.core.ui)
    api(projects.data.account)
    api(projects.domain.visualtransformation)
}