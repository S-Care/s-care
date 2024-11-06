plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.chaquo.python")
}

android {
    namespace = "com.scare"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.scare"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }

        ndk {
            // On Apple silicon, you can omit x86_64.
            abiFilters += listOf("arm64-v8a", "x86_64")
        }

    }

    flavorDimensions += "pyVersion"
    productFlavors {
        create("py310") { dimension = "pyVersion" }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

chaquopy {
    defaultConfig {
        version = "3.10"

        pip {
            install("src/main/python/numpy-1.23.3-0-cp310-cp310-android_21_arm64_v8a.whl")
            install("scipy")
        }
    }
    productFlavors {
        getByName("py310") { version = "3.10" }
    }
    sourceSets {
        getByName("main") {
            srcDir("src/main/python")
        }
    }
}

dependencies {
    // https://developer.android.com/jetpack/androidx/releases/concurrent?hl=ko
    implementation(libs.androidx.concurrent.futures.ktx)
    // https://developer.android.com/jetpack/androidx/releases/health?hl=ko
    implementation(libs.androidx.health.services.client)
    // https://developer.android.com/kotlin/coroutines?hl=ko
    implementation(libs.kotlinx.coroutines.android)
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
    implementation(libs.kotlinx.coroutines.core)
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-guava
    implementation(libs.kotlinx.coroutines.guava)
    // https://developer.android.com/jetpack/androidx/releases/datastore?hl=ko
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.androidx.compose.material)
    // https://developer.android.com/jetpack/androidx/releases/lifecycle?hl=ko
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // https://mvnrepository.com/artifact/com.google.accompanist/accompanist-permissions
    implementation(libs.accompanist.permissions)

    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.androidx.material.icons.core.android)

    // https://mvnrepository.com/artifact/androidx.compose.ui/ui-tooling-preview
    implementation(libs.ui.tooling.preview)
    // https://developer.android.com/jetpack/androidx/releases/compose-ui?hl=ko
    implementation(libs.ui)

    implementation(libs.androidx.wear.tooling.preview)
    // https://developer.android.com/jetpack/androidx/releases/activity?hl=ko
    implementation(libs.androidx.activity.ktx)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)
    // https://developer.android.com/jetpack/androidx/releases/work?hl=ko
    implementation(libs.work.runtime.ktx)

    implementation(libs.accompanist.permissions)

    // https://mvnrepository.com/artifact/com.google.android.gms/play-services-wearable
    implementation(libs.gms.play.services.wearable)
}