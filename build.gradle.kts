// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.android.library") version "8.5.0" apply false
    kotlin("kapt") version "1.9.24" apply false
    alias(libs.plugins.hilt) apply false
}