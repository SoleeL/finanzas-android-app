pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "finanzas"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")

include(":core:common")
include(":core:component")
include(":core:database")
include(":core:formatter")
include(":core:model")
include(":core:ui")

include(":data:expense")
include(":data:account")
include(":data:preferences")

include(":domain:account")
include(":domain:formatdate")
include(":domain:stats")
include(":domain:transactions")
include(":domain:validation")

//include(":feature:accounts")
//include(":feature:createtransaction")
//include(":feature:profile")
//include(":feature:stats")
//include(":feature:transactions")

// NUEVAS
include(":feature:configuration")
include(":feature:createaccount")
include(":feature:createexpense")
include(":feature:home")
include(":feature:launch")
