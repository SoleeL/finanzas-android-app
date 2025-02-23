plugins {
    alias(libs.plugins.finanzas.android.library)
}

android {
    namespace = "com.soleel.finanzas.core.model"
}

dependencies {
    api(projects.core.common)
    api(projects.core.ui)
}