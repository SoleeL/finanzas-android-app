plugins {
    id("conventionPluginsApp.android.core")
}

android {
    namespace = "com.soleel.finanzas.core.database"
}

dependencies {
    api(projects.core.common)
}