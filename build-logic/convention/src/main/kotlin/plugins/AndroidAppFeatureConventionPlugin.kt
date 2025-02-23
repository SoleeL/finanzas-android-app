package plugins

import extensions.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class AndroidAppFeatureConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("conventionPluginsApp.android.lib")
                apply("conventionPluginsApp.android.lib.compose")
                apply("conventionPluginsApp.android.hilt")
            }

            dependencies {
                add("implementation", project(":domain:validation"))
                add("implementation", project(":domain:transformation"))

                add("implementation", project(":data:account"))
                add("implementation", project(":data:transaction"))

                add("implementation", project(":core:common"))
                add("implementation", project(":core:ui"))

                add("implementation", versionCatalog().findLibrary("hilt-navigation-compose").get())

                add("implementation", versionCatalog().findLibrary("androidx-lifecycle-runtimeCompose").get())
                add("implementation", versionCatalog().findLibrary("androidx-lifecycle-viewModelCompose").get())

                add("implementation", versionCatalog().findLibrary("kotlinx-coroutines-android").get())
            }
        }
    }
}