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
        versionName = "0.2.0"
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

repositories {
    google()
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(project(":data"))
    implementation(project(":evaluate"))
    implementation(project(":opencv"))
    implementation(project(":preprocess"))
    implementation(libs.material)
    implementation(libs.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)
    implementation(libs.swiperefreshlayout)
    implementation(libs.photoview)
}