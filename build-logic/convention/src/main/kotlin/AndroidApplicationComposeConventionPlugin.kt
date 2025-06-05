import com.android.build.api.dsl.ApplicationExtension
import com.denebchorny.buildlogic.convention.extensions.android
import com.denebchorny.buildlogic.convention.extensions.configureAndroidCompose
import com.denebchorny.buildlogic.convention.extensions.alias
import com.denebchorny.buildlogic.convention.extensions.libs
import com.denebchorny.buildlogic.convention.extensions.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            alias(libs.plugins.android.application)
            alias(libs.plugins.kotlin.compose)
        }

        android<ApplicationExtension> {
            configureAndroidCompose(this)
        }
    }
}

