import com.denebchorny.buildlogic.convention.configs.Config
import com.denebchorny.buildlogic.convention.extensions.libs

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.denebchorny.androidApplication)
    alias(libs.plugins.denebchorny.androidApplicationCompose)
    alias(libs.plugins.denebchorny.jvmCoreDomain)
    alias(libs.plugins.denebchorny.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    defaultConfig {
        applicationId = Config.App.id
        versionCode = Config.project.versionCode
        versionName = Config.project.versionName
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("debug")
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../keys/space_flight_news.keystore")
        }
    }

    namespace = Config.App.id
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.network.di)

    implementation(projects.feature.articles.data)
    implementation(projects.feature.articles.presentation)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.multidex)
    implementation(libs.timber)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.ui.tooling.preview)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.ui.tooling)
    testImplementation(libs.junit)
}