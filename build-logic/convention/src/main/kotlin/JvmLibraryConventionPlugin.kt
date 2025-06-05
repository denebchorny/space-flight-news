import com.denebchorny.buildlogic.convention.extensions.alias
import com.denebchorny.buildlogic.convention.extensions.configureKotlinJvm
import com.denebchorny.buildlogic.convention.extensions.libs
import com.denebchorny.buildlogic.convention.extensions.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            alias(libs.plugins.jetbrains.kotlin.jvm)
            alias(libs.plugins.denebchorny.androidLint)
            alias(libs.plugins.ksp)
        }

        configureKotlinJvm()
    }
}

