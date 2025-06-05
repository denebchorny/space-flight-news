package com.denebchorny.buildlogic.convention.extensions

import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency

fun PluginManager.alias(dependency: Provider<PluginDependency>) {
    apply(dependency.get().pluginId)
}

fun PluginManager.id(pluginId: String) {
    apply(pluginId)
}

fun PluginManager.hasPlugin(plugin: Provider<PluginDependency>): Boolean =
    hasPlugin(plugin.get().pluginId)