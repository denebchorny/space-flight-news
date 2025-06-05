import com.android.build.gradle.api.AndroidBasePlugin
import com.denebchorny.buildlogic.convention.extensions.alias
import com.denebchorny.buildlogic.convention.extensions.implementation
import com.denebchorny.buildlogic.convention.extensions.ksp
import com.denebchorny.buildlogic.convention.extensions.libs
import com.denebchorny.buildlogic.convention.extensions.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            alias(libs.plugins.ksp)
        }

        dependencies {
            ksp(libs.hilt.compiler)
            implementation(libs.hilt.core)
        }

        /** Add support for Android modules, based on [AndroidBasePlugin] */
        pluginManager.withPlugin("com.android.base") {
            pluginManager.apply("dagger.hilt.android.plugin")
            dependencies {
                implementation(libs.hilt.android)
            }
        }
    }
}
