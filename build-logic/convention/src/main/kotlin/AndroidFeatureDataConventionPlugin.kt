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

class AndroidFeatureDataConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            alias(libs.plugins.denebchorny.androidLibrary)
            alias(libs.plugins.denebchorny.jvmCoreDomain)
            alias(libs.plugins.denebchorny.hilt)
        }

        android<LibraryExtension> {
            testOptions.unitTests.isIncludeAndroidResources = true
            testOptions.unitTests.isReturnDefaultValues = true
        }
    }
}
