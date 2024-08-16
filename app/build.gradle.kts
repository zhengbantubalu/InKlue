plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.bupt.inklue"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bupt.inklue"
        minSdk = 29
        targetSdk = 35
        versionCode = 3
        versionName = "0.1.2"
        ndk {
            abiFilters.add("arm64-v8a")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro",
                    getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
}

dependencies {
    implementation(project(":opencv"))
    implementation(project(":evaluate"))
    implementation(project(":preprocess"))
    implementation(libs.material)
    implementation(libs.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)
    implementation(libs.swiperefreshlayout)
    implementation(libs.photoview)
}