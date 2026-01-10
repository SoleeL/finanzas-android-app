plugins {
    alias(libs.plugins.finanzas.android.library)
    alias(libs.plugins.finanzas.android.library.compose)
    alias(libs.plugins.finanzas.android.hilt)
}

android {
    namespace = "com.soleel.finanzas.feature.createaccount"
}

dependencies {
    api(projects.core.common)
    api(projects.core.component)
    api(projects.core.ui)
    api(projects.data.account)
    api(projects.domain.validation)
}