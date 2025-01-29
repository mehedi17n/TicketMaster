plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.ticketmaster"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ticketmaster"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
        dataBinding = true
        buildConfig = true
    }
}

//Resolution strategy that force the use of the corresponding libraries that causes duplicity
configurations.configureEach {
    resolutionStrategy.force("androidx.legacy:legacy-support-v4:1.0.0")
    resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10")
    resolutionStrategy.force("org.bouncycastle:bcpkix-jdk15on:1.70")
}

//configurations {
//    all*.exclude module: 'bcprov-jdk16'
//}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Tickets SDK
    implementation (libs.secure.entry)
//    implementation (libs.tickets)

    // Accounts SDK
    implementation(libs.authentication)
    // Retail SDK
    implementation(libs.purchase)
    implementation(libs.prepurchase)
    implementation(libs.discoveryapi)
    implementation(libs.foundation)



    //newly added dependency
    implementation (libs.retrofit)
    implementation (libs.converter.gson)


    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")



}