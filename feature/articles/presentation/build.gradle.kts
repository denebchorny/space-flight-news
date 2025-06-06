import com.denebchorny.buildlogic.convention.extensions.libs

plugins {
    alias(libs.plugins.denebchorny.androidFeaturePresentation)
}

android {
    namespace = "com.denebchorny.feature.articles.presentation"
}

dependencies {
    implementation(projects.feature.articles.domain)

    implementation(libs.androidx.compose.lifecycle.runtime)
    implementation(libs.androidx.compose.lifecycle.viewmodel)

    implementation(enforcedPlatform(libs.kotlinx.serialization.bom))
    implementation(libs.kotlinx.serialization.json)

    implementation(enforcedPlatform(libs.coil.bom))
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.androidx.compose.navigation)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}