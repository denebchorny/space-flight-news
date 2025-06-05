plugins {
    alias(libs.plugins.denebchorny.androidFeaturePresentation)
}

android {
    namespace = "com.denebchorny.feature.articles.presentation"
}

dependencies {
    implementation(projects.feature.articles.domain)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}