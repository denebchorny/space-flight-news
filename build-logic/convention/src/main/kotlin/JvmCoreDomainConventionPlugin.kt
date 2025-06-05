import com.denebchorny.buildlogic.convention.extensions.api
import com.denebchorny.buildlogic.convention.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class JvmCoreDomainConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        dependencies {
            api(project(":core:common:jvm"))
            api(project(":core:model"))

            api(libs.kotlinx.coroutines.core)
        }
    }
}
