package com.denebchorny.buildlogic.convention.extensions

import com.android.build.api.dsl.CommonExtension
import com.denebchorny.buildlogic.convention.configs.Config
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        compileSdk = Config.sdk.compile
        defaultConfig.minSdk = Config.sdk.min

        compileOptions.sourceCompatibility = Config.JAVA.version
        compileOptions.targetCompatibility = Config.JAVA.version

        configureKotlin<KotlinBaseExtension>()
    }
}

/**
 * Configure base Kotlin options for JVM (non-Android)
 */
internal fun Project.configureKotlinJvm() {
    java {
        sourceCompatibility = Config.JAVA.version
        targetCompatibility = Config.JAVA.version
    }

    configureKotlin<KotlinJvmProjectExtension>()
}

/**
 * Configure base Kotlin options
 */
private inline fun <reified T : KotlinBaseExtension> Project.configureKotlin() = configure<T> {
    // Treat all Kotlin warnings as errors (disabled by default)
    // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
    val warningsAsErrors: String? by project
    when (this) {
        is KotlinAndroidProjectExtension -> compilerOptions
        is KotlinJvmProjectExtension -> compilerOptions
        else -> TODO("Unsupported project extension $this ${T::class}")
    }.apply {
        jvmTarget = Config.JVM.version
        allWarningsAsErrors = warningsAsErrors.toBoolean()

        // Enable experimental coroutines APIs, including Flow
        freeCompilerArgs.add("-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
        freeCompilerArgs.add("-Xlint:deprecation")
    }
}