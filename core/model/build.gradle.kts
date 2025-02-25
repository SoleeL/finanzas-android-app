plugins {
    alias(libs.plugins.finanzas.android.library)
}

android {
    namespace = "com.soleel.finanzas.core.model"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.ui)
}