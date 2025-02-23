package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class AndroidAppDataConvetionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("conventionPluginsApp.android.lib")
                apply("conventionPluginsApp.android.hilt")
            }

            dependencies {
                add("implementation", project(":core:database"))
//                add("implementation", project(":core:network"))
            }
        }
    }
}