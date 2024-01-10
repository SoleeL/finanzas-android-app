package plugins

import extensions.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidAppDomainConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("conventionPluginsApp.android.lib")
                apply("conventionPluginsApp.android.hilt")
            }

            dependencies {
                add("implementation", project(":data:paymentaccount"))
                add("implementation", project(":data:transaction"))

                add("implementation", project(":core:common"))
                add("implementation", project(":core:ui"))

                add("implementation", versionCatalog().findLibrary("kotlinx-coroutines-android").get())
            }
        }
    }
}