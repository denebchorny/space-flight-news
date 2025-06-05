plugins {
    alias(libs.plugins.denebchorny.androidLibrary)
    alias(libs.plugins.denebchorny.androidLibraryCompose)
}

android {
    namespace = "com.denebchorny.core.common.android"
}

dependencies {
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}