plugins {
    id("conventionPluginsApp.android.core")
}

android {
    namespace = "com.soleel.finanzas.core.ui"
}

dependencies {
    api(projects.core.common)
}