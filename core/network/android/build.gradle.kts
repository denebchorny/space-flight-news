plugins {
    alias(libs.plugins.denebchorny.androidLibrary)
    alias(libs.plugins.denebchorny.hilt)
}

android {
    namespace = "com.denebchorny.core.network.android"
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.retrofit.core)

    implementation(libs.okhttp.logging.interceptor)

    implementation(enforcedPlatform(libs.kotlinx.serialization.bom))
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}