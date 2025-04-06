package extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project


fun Project.configureBuildTypes(
    extension: CommonExtension<*, *, *, *>
) {
    extension.apply {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                isJniDebuggable = false
            }

            getByName("debug") {
                isMinifyEnabled = false
                isJniDebuggable = true

                if (extension is ApplicationExtension) {
                    extension.buildTypes.getByName("debug").apply {
                        applicationIdSuffix = ".debug"
                        resValue("string", "app_name", "finanzas_debug")
                    }
                }
            }

            maybeCreate("demo").apply {
                initWith(getByName("debug"))
                if (extension is ApplicationExtension) {
                    extension.buildTypes.getByName("demo").apply {
                        applicationIdSuffix = ".demo"
                        resValue("string", "app_name", "finanzas_demo")
                    }
                }
            }
        }
    }
}