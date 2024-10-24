plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services") // Correct place for the Google Services plugin
    id("kotlin-kapt")
}

android {
    namespace = "com.sameer.silentecho"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }


    defaultConfig {
        applicationId = "com.sameer.silentecho"
        minSdk = 29
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation (libs.sdp.android)
    implementation (libs.ssp.android)

    implementation (libs.imagepicker)
    implementation (libs.tensorflow.lite)

    implementation ("org.tensorflow:tensorflow-lite:2.9.0")
    implementation ("org.tensorflow:tensorflow-lite-support:0.3.1")

    //piechart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

//    //api
//    // Retrofit for making API requests
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
//
//    // Gson for handling JSON responses
//    implementation ("com.google.code.gson:gson:2.8.6")
//
//    implementation("com.squareup.okhttp3:okhttp:4.9.2")

//    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
//    implementation ("org.json:json:20210307")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Add Firebase dependencies here if needed
    // implementation 'com.google.firebase:firebase-analytics:21.1.0' // Example

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
