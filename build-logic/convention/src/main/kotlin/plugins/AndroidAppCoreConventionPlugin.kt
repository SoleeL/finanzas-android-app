package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidAppCoreConventionPlugin : Plugin<Project>{

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("conventionPluginsApp.android.lib")
                apply("conventionPluginsApp.android.lib.compose")
                apply("conventionPluginsApp.android.hilt")
                apply("conventionPluginsApp.android.room")
            }
        }
    }
}

