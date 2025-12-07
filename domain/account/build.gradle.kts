plugins {
    alias(libs.plugins.finanzas.android.library)
    alias(libs.plugins.finanzas.android.hilt)
}

android {
    namespace = "com.soleel.finanzas.domain.account"
}

dependencies{
    api(projects.data.account)
    api(projects.data.expense)
}