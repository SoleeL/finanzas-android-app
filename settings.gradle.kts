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
include(":core:database")
include(":core:ui")

include(":data:paymentaccount")
include(":data:transaction")

include(":domain:validation")
include(":domain:transformation")

include(":feature:transactions")
include(":feature:transactioncreate")
include(":feature:paymentaccounts")
include(":feature:paymentaccountcreate")
include(":feature:stats")
include(":feature:profile")
include(":feature:add")
include(":feature:cancelalert")

