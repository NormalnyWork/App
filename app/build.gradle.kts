plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.normalnywork.plants"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.normalnywork.plants"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // AndroidX
    implementation(libs.bundles.androidx)
    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    // Navigation
    implementation(libs.bundles.decompose)
    // Network
    implementation(libs.bundles.retrofit)
    implementation(libs.okhttp)
    // DI
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
    // Coroutines
    implementation(libs.coroutines)
    // Toolkit
    implementation(libs.splitties)
    // Image loading
    implementation(libs.bundles.coil)
    // Blur
    implementation(libs.haze)
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
}