package config

import org.gradle.api.JavaVersion

object Config {
    val android = AndroidConfig(
        minSdkVersion = 30,
        targetSdkVersion = 33,
        compileSdkVersion = 34,
        applicationId = "com.soleel.finanzas",
        versionCode = 1,
        versionName = "1.0",
        nameSpace = "com.soleel.finanzas"
    )

    val jvm = JvmConfig(
        javaVersion = JavaVersion.VERSION_1_8,
        kotlinJvm = JavaVersion.VERSION_1_8.toString(),
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    )
}

data class AndroidConfig(
    val minSdkVersion : Int,
    val targetSdkVersion : Int,
    val compileSdkVersion : Int,
    val applicationId : String,
    val versionCode : Int,
    val versionName : String,
    val nameSpace: String
)

data class JvmConfig(
    val javaVersion : JavaVersion,
    val kotlinJvm : String,
    val freeCompilerArgs : List<String>
)