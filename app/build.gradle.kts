plugins {
    alias(libs.plugins.finanzas.android.application)
    alias(libs.plugins.finanzas.android.application.compose)
    alias(libs.plugins.finanzas.android.hilt)
}

android {
    namespace = "com.soleel.finanzas"
}

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.stats)
    implementation(projects.feature.accounts)
    implementation(projects.feature.profile)
    implementation(projects.feature.add)
//    implementation(projects.feature.addmodal)
    implementation(projects.feature.cancelalert)
    implementation(projects.feature.paymentaccountcreate)
    implementation(projects.feature.transactioncreate)
}