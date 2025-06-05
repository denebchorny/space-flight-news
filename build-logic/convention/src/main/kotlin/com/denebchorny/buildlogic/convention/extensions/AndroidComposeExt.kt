package com.denebchorny.buildlogic.convention.extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures.compose = true
        testOptions.unitTests.isIncludeAndroidResources = true
    }

    composeCompiler {
        enableComposeMetrics(this)
        enableCompilerReport(this)
    }

    dependencies {
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.ui.tooling.preview)

        androidTestImplementation(platform(libs.androidx.compose.bom))
        debugImplementation(libs.androidx.compose.ui.tooling)
    }
}