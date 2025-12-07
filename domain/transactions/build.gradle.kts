plugins {
    alias(libs.plugins.finanzas.android.library)
    alias(libs.plugins.finanzas.android.hilt)
}

android {
    namespace = "com.soleel.finanzas.domain.transactions"
}

dependencies{
    api(projects.data.account)
    api(projects.data.expense)
}