plugins {
    alias(libs.plugins.denebchorny.androidFeatureDomain)
    alias(libs.plugins.denebchorny.hilt)
}

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
}