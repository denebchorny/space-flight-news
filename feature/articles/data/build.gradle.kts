plugins {
    alias(libs.plugins.denebchorny.androidFeatureData)
    alias(libs.plugins.denebchorny.hilt)
}

android {
    namespace = "com.denebchorny.feature.articles.data"
}

dependencies {
    implementation(projects.feature.articles.domain)
    implementation(projects.core.network.android)

    implementation(enforcedPlatform(libs.kotlinx.serialization.bom))
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}