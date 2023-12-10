plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.odiacalander"
    compileSdk = 34

    defaultConfig {
        resourceConfigurations.addAll(listOf("or", "hi", "en_US"))
        applicationId = "com.odiacalander"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
//        debug {
//            isMinifyEnabled = true
//            isShrinkResources = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
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
kapt {
    correctErrorTypes = true
}

dependencies {
    //permission
    implementation("com.google.accompanist:accompanist-permissions:0.33.2-alpha")
    //webscraping
    implementation(libs.jsoup)
    implementation(libs.coil.compose)
    implementation(libs.gson)

    //lang-localization
    implementation(libs.appcompat)
//Splash Api
    implementation(libs.splashscreen)
//google ads
    implementation(libs.play.services.ads)
    // ViewModel
    implementation(libs.lifecycle.viewmodel.ktx)
    // ViewModel utilities for Compose
    implementation(libs.lifecycle.viewmodel.compose)
    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
//Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    //coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    //RoomDB
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // WorkManager
    implementation(libs.workManager)

    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.bundles.core)
    //MMKV (Ultrafast Key-Value storage)
    implementation(libs.mmkv)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}