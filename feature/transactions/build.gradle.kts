plugins {
    alias(libs.plugins.finanzas.android.library)
    alias(libs.plugins.finanzas.android.library.compose)
    alias(libs.plugins.finanzas.android.hilt)
}

android {
    namespace = "com.soleel.finanzas.feature.transactions"
}

dependencies {
    api(projects.core.common)
    api(projects.core.model)
    api(projects.core.ui)
    api(projects.domain.formatdate)
    api(projects.domain.visualtransformation)
    api(projects.domain.transactions)
}