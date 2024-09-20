plugins {
    alias(libs.plugins.androidLibrary)
}

android {
    namespace = "com.bupt.evaluate"
    compileSdk = 35
    defaultConfig.minSdk = 29
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(project(":opencv"))
}