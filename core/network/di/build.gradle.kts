plugins {
    alias(libs.plugins.denebchorny.androidLibrary)
    alias(libs.plugins.denebchorny.hilt)
}

android {
    namespace = "com.denebchorny.core.network.di"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.network.android)

    implementation(enforcedPlatform(libs.kotlinx.serialization.bom))
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.okhttp.logging.interceptor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}