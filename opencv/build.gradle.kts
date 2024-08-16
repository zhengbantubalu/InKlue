plugins {
    alias(libs.plugins.androidLibrary)
}

android {
    namespace = "org.opencv"
    compileSdk = 35
    defaultConfig.minSdk = 29

    buildFeatures {
        aidl = true
        buildConfig = true
    }

    sourceSets {
        named("main") {
            aidl.srcDir("src/main/java")
        }
    }
}