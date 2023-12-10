plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
}
buildscript {
    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.49")
    }
}


true // Needed to make the Suppress annotation work for the plugins block