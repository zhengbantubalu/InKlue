plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.bupt.inklue"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bupt.inklue"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        ndk {
            abiFilters.add("arm64-v8a") //只生成arm64-v8a架构的apk
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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