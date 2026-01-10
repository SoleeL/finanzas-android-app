plugins {
    alias(libs.plugins.finanzas.android.application)
    alias(libs.plugins.finanzas.android.application.compose)
    alias(libs.plugins.finanzas.android.hilt)
}

android {
    namespace = "com.soleel.finanzas"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.common)
    implementation(projects.core.model)

    implementation(projects.data.preferences)

    implementation(projects.feature.configuration)
    implementation(projects.feature.createexpense)
    implementation(projects.feature.createaccount)
    implementation(projects.feature.home)
    implementation(projects.feature.launch)

//    implementation(projects.feature.add)
//    implementation(projects.feature.accounts)
//    implementation(projects.feature.createtransaction)
//    implementation(projects.feature.profile)
//    implementation(projects.feature.stats)
//    implementation(projects.feature.transactions)
}