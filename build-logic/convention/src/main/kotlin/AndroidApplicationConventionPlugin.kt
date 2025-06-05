import com.android.build.api.dsl.ApplicationExtension
import com.denebchorny.buildlogic.convention.configs.Config
import com.denebchorny.buildlogic.convention.extensions.android
import com.denebchorny.buildlogic.convention.extensions.configureKotlinAndroid
import com.denebchorny.buildlogic.convention.extensions.alias
import com.denebchorny.buildlogic.convention.extensions.libs
import com.denebchorny.buildlogic.convention.extensions.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            alias(libs.plugins.android.application)
            alias(libs.plugins.kotlin.android)
            alias(libs.plugins.denebchorny.androidLint)
        }

        android<ApplicationExtension> {
            configureKotlinAndroid(this)

            defaultConfig.targetSdk = Config.sdk.target
            defaultConfig.multiDexEnabled = true
            defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

            testOptions.animationsDisabled = true
            buildFeatures.buildConfig = true
        }
    }
}

