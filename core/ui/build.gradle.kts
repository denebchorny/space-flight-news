plugins {
    alias(libs.plugins.denebchorny.androidLibrary)
    alias(libs.plugins.denebchorny.androidLibraryCompose)
}

android {
    namespace = "com.denebchorny.core.ui"
}

dependencies {
    api(projects.core.common.android)
    api(projects.core.designsystem)

    api(libs.androidx.core.ktx)

    implementation(enforcedPlatform(libs.coil.bom))
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}