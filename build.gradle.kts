// Top-level build file where you can add configuration options common to all sub-projects/modules.
//buildscript {
//
//    repositories {
//
//        google()
//        jcenter()
//        mavenCentral()
//        maven { url = uri("https://jitpack.io") }
//        maven {
//            url = uri("https://maven.google.com")
//        }
//        maven { url = uri("https://maven.fabric.io/public") }
//    }
//}


plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false

}
