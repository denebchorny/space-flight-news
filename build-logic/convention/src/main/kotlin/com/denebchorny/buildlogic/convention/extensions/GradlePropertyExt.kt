package com.denebchorny.buildlogic.convention.extensions

import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension


fun Project.enableComposeMetrics(plugin: ComposeCompilerGradlePluginExtension) {
    with(plugin) {
        gradleProperty(
            "enableComposeCompilerMetrics",
            "compose-metrics",
            metricsDestination
        )
    }
}

fun Project.enableCompilerReport(plugin: ComposeCompilerGradlePluginExtension) {
    with(plugin) {
        gradleProperty(
            "enableComposeCompilerReports",
            "compose-reports",
            reportsDestination
        )
    }
}

fun Project.gradleProperty(
    propertyName: String,
    directory: String,
    directoryProperty: DirectoryProperty
) {
    fun Provider<String>.onlyIfTrue() =
        flatMap { provider { it.takeIf(String::toBoolean) } }

    fun Provider<*>.relativeToRootProject(dir: String) = flatMap {
        rootProject.layout.buildDirectory.dir(projectDir.toRelativeString(rootDir))
    }.map { it.dir(dir) }

    project.providers.gradleProperty(propertyName).onlyIfTrue()
        .relativeToRootProject(directory)
        .let(directoryProperty::set)
}