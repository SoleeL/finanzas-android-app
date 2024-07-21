plugins {
    alias(libs.plugins.finanzas.android.application)
    alias(libs.plugins.finanzas.android.application.compose)
    alias(libs.plugins.finanzas.android.hilt)
}

android {
    namespace = "com.soleel.finanzas"
}

dependencies {
    implementation(projects.feature.add)
    implementation(projects.feature.accounts)
    implementation(projects.feature.createaccount)
    implementation(projects.feature.profile)
    implementation(projects.feature.stats)
    implementation(projects.feature.transactioncreate)
    implementation(projects.feature.transactions)
}