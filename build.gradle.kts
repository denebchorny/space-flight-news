// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) version (libs.versions.ksp) apply false
    alias(libs.plugins.kotlin.serialization) version (libs.versions.kotlin) apply false
    //alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.hilt) version (libs.versions.hilt) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}