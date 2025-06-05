import com.denebchorny.buildlogic.convention.extensions.alias
import com.denebchorny.buildlogic.convention.extensions.libs
import com.denebchorny.buildlogic.convention.extensions.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFeatureDomainConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            alias(libs.plugins.denebchorny.jvmLibrary)
            alias(libs.plugins.denebchorny.jvmCoreDomain)
        }
    }
}