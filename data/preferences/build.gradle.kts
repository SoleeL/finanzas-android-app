plugins {
    alias(libs.plugins.finanzas.android.library)
    alias(libs.plugins.finanzas.android.hilt)
}

android {
    namespace = "com.soleel.finanzas.data.preferences"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.datastore.preferences)
}