plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id ("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}


android {
    namespace = "com.example.churchproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.churchproject"
        minSdk = 24
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

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.play.services.location)
    // Versions
    val kotlin_version = "1.7.0"
    val compose_version = "1.0.0-rc02"
    val paging_version = "3.1.1"
    val lottie_version = "4.2.0"
    val coroutines_version = "1.5.2"
    val hilt_version = "2.48"
    val retrofit_version = "2.9.0"
    val okhttp_version = "4.9.1"
    val gson_version = "2.8.8"
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit and OkHttp
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:okhttp:$okhttp_version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp_version")
    implementation("com.google.code.gson:gson:$gson_version")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt ("com.google.dagger:hilt-android-compiler:$hilt_version")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")

    // RxJava
    implementation("io.reactivex.rxjava2:rxjava:2.2.19")
    implementation("com.jakewharton.rxbinding2:rxbinding:2.0.0")

    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")

    implementation ("de.hdodenhof:circleimageview:3.1.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")


    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation ("androidx.appcompat:appcompat:1.2.0")
    implementation ("com.google.zxing:core:3.3.3")

    implementation ("com.github.kshitijskumar:Ticker:1.0.0")

}