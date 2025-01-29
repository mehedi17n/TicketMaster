// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

buildscript {

    repositories {
        mavenCentral()
        google()
        maven { url = uri ("https://jitpack.io") }
    }

    dependencies {
        classpath ("com.android.tools.build:gradle:8.4.2")
        classpath ("com.github.dcendents:android-maven-gradle-plugin:1.5")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        classpath ("com.facebook.testing.screenshot:plugin:0.13.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}