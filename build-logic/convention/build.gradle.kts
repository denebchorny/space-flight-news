import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.denebchorny.buildlogic.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    //compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        /*register("androidApplication") {
            id = "denebchorny.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
            version = "1.0.0"
        }
        register("androidApplicationCompose") {
            id = "denebchorny.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
            version = "1.0.0"
        }
        register("androidLibrary") {
            id = "denebchorny.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
            version = "1.0.0"
        }
        register("androidLibraryCompose") {
            id = "denebchorny.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
            version = "1.0.0"
        }
        register("JvmCoreDomain") {
            id = "denebchorny.jvm.coreDomain"
            implementationClass = "JvmCoreDomainConventionPlugin"
            version = "1.0.0"
        }
        register("jvmLibrary") {
            id = "denebchorny.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
            version = "1.0.0"
        }
        register("androidLint") {
            id = "denebchorny.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
            version = "1.0.0"
        }
        register("hilt") {
            id = "denebchorny.hilt"
            implementationClass = "HiltConventionPlugin"
            version = "1.0.0"
        }
        register("androidFeatureDomain") {
            id = "denebchorny.android.feature.domain"
            implementationClass = "AndroidFeatureDomainConventionPlugin"
            version = "1.0.0"
        }
        register("androidFeatureData") {
            id = "denebchorny.android.feature.data"
            implementationClass = "AndroidFeatureDataConventionPlugin"
            version = "1.0.0"
        }
        register("androidFeaturePresentation") {
            id = "denebchorny.android.feature.presentation"
            implementationClass = "AndroidFeaturePresentationConventionPlugin"
            version = "1.0.0"
        }*/
    }
}