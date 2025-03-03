package extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures.compose = true
        composeOptions.kotlinCompilerExtensionVersion =
            versionCatalog().findVersion("androidx-compose-compiler").get().toString()

        dependencies {
            add("implementation", versionCatalog().findLibrary("androidx-activity-compose").get())
            add("implementation", versionCatalog().findLibrary("compose-lifecycle").get())
            add("implementation", versionCatalog().findLibrary("compose-navigation").get())

            add("implementation", platform(versionCatalog().findLibrary("compose-bom").get()))
//            add("implementation", versionCatalog().findLibrary("compose-ui").get())
//            add("implementation", versionCatalog().findLibrary("compose-ui-text").get())
//            add("implementation", versionCatalog().findLibrary("compose-ui-graphics").get())
//            add("implementation", versionCatalog().findLibrary("compose-ui-tooling-preview").get())
//            add("implementation", versionCatalog().findLibrary("compose-material3").get())

            add("implementation", versionCatalog().findBundle("compose").get())

            add("androidTestImplementation", platform(versionCatalog().findLibrary("compose-bom").get()))
            add("testImplementation", versionCatalog().findLibrary("compose-ui-test-junit4").get())
            add("debugImplementation", versionCatalog().findLibrary("compose-ui-tooling").get())
            add("debugImplementation", versionCatalog().findLibrary("compose-ui-test-manifest").get()
            )
        }
    }
}