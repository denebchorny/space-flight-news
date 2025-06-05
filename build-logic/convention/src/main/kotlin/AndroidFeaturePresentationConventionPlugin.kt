import com.android.build.api.dsl.LibraryExtension
import com.denebchorny.buildlogic.convention.extensions.alias
import com.denebchorny.buildlogic.convention.extensions.android
import com.denebchorny.buildlogic.convention.extensions.api
import com.denebchorny.buildlogic.convention.extensions.implementation
import com.denebchorny.buildlogic.convention.extensions.libs
import com.denebchorny.buildlogic.convention.extensions.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeaturePresentationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            alias(libs.plugins.denebchorny.androidLibrary)
            alias(libs.plugins.denebchorny.androidLibraryCompose)
            alias(libs.plugins.denebchorny.jvmCoreDomain)
            alias(libs.plugins.denebchorny.hilt)
        }

        android<LibraryExtension> {
            testOptions.animationsDisabled = true
        }

        dependencies {
            api(project(":core:ui"))
            api(project(":core:common:android"))

            implementation(libs.androidx.compose.hilt.navigation)
            implementation(libs.androidx.compose.lifecycle.runtime)
            implementation(libs.androidx.compose.lifecycle.viewmodel)
        }
    }
}
