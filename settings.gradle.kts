rootProject.name = "InKlue"
include(":app")
include(":data")
include(":evaluate")
include(":opencv")
include(":preprocess")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}