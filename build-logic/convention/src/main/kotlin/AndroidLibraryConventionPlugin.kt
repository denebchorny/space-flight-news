import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.denebchorny.buildlogic.convention.extensions.alias
import com.denebchorny.buildlogic.convention.extensions.android
import com.denebchorny.buildlogic.convention.extensions.androidComponents
import com.denebchorny.buildlogic.convention.extensions.androidTestImplementation
import com.denebchorny.buildlogic.convention.extensions.configureKotlinAndroid
import com.denebchorny.buildlogic.convention.extensions.libs
import com.denebchorny.buildlogic.convention.extensions.plugins
import com.denebchorny.buildlogic.convention.extensions.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            alias(libs.plugins.android.library)
            alias(libs.plugins.kotlin.android)
            alias(libs.plugins.kotlin.serialization)
            alias(libs.plugins.denebchorny.androidLint)
        }

        android<LibraryExtension> {
            configureKotlinAndroid(this)
            defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            testOptions.animationsDisabled = true
        }

        androidComponents<LibraryAndroidComponentsExtension> {
            beforeVariants {
                it.enableAndroidTest = it.enableAndroidTest && project.projectDir
                    .resolve("src/androidTest")
                    .exists()
            }
        }

        dependencies {
            /**
             * kotlin("test"): This is a Kotlin DSL shorthand for referencing
             * the Kotlin test library. It automatically resolves the correct version
             * of the library based on your project's configuration.
             * */
            androidTestImplementation(kotlin("test"))
            testImplementation(kotlin("test"))
        }
    }
}

