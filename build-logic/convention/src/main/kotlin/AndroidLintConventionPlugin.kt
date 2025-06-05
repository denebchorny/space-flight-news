import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Lint
import com.denebchorny.buildlogic.convention.extensions.alias
import com.denebchorny.buildlogic.convention.extensions.android
import com.denebchorny.buildlogic.convention.extensions.hasPlugin
import com.denebchorny.buildlogic.convention.extensions.libs
import com.denebchorny.buildlogic.convention.extensions.lint
import com.denebchorny.buildlogic.convention.extensions.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            when {
                hasPlugin(libs.plugins.android.application) -> {
                    android<ApplicationExtension> {
                        lint(Lint::configure)
                    }
                }

                hasPlugin(libs.plugins.android.library) -> {
                    android<LibraryExtension> {
                        lint(Lint::configure)
                    }
                }

                else -> {
                    alias(libs.plugins.lint)
                    lint {
                        configure()
                    }
                }
            }
        }
    }
}

private fun Lint.configure() {
    xmlReport = true
    checkDependencies = true
    abortOnError = false
}