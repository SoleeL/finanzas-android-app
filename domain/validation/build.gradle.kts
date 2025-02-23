plugins {
    alias(libs.plugins.finanzas.android.library)
}

android {
    namespace = "com.soleel.finanzas.domain.validation"
}

dependencies {
    api(projects.core.ui)
    api(projects.core.model)
}