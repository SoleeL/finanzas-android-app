plugins {
    alias(libs.plugins.finanzas.android.library)
    alias(libs.plugins.finanzas.android.hilt)
}

android {
    namespace = "com.soleel.finanzas.data.trasaction"
}

dependencies {
    api(projects.core.database)
    api(projects.core.model)
}