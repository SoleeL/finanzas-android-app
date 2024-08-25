plugins {
    alias(libs.plugins.finanzas.android.library)
    alias(libs.plugins.finanzas.android.hilt)
    alias(libs.plugins.finanzas.android.room)
}

android {
    namespace = "com.soleel.finanzas.core.database"
}

dependencies {
    api(projects.core.model)
}