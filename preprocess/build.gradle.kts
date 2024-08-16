plugins {
    alias(libs.plugins.androidLibrary)
}

android {
    namespace = "com.bupt.preprocess"
    compileSdk = 35
    defaultConfig.minSdk = 29
}

dependencies {
    implementation(project(":opencv"))
}