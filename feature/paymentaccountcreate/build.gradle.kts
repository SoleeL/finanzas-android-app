plugins {
    alias(libs.plugins.finanzas.android.library)
    alias(libs.plugins.finanzas.android.library.compose)
    alias(libs.plugins.finanzas.android.hilt)
}

android {
    namespace = "com.soleel.finanzas.feature.paymentaccountcreate"
}

dependencies {
    api(projects.core.common)
    api(projects.data.paymentaccount)
    api(projects.data.transaction)
    api(projects.domain.validation)
    api(projects.domain.transformation)
}