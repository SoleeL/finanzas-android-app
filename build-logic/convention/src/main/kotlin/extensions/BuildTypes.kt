package extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project

fun Project.configureBuildTypes(
    appExtension: ApplicationExtension
) {
    appExtension.apply {
        buildTypes {
            release {
                isMinifyEnabled = false
                isJniDebuggable = false
            }

            debug {
                isMinifyEnabled = false
                isJniDebuggable = true
                applicationIdSuffix = ".debug"
                resValue("string", "app_name", "finanzas_debug")
            }
        }
    }
}

fun Project.configureBuildTypes(
    libraryExtension: LibraryExtension
) {
    return
}