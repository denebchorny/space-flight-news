package com.denebchorny.buildlogic.convention.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Lint
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.PluginManager
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

val Project.libs: LibrariesForLibs
    get() = the()

fun Project.plugins(block: PluginManager.() -> Unit) {
    pluginManager.block()
}

inline fun <reified T> Project.android(crossinline block: T.() -> Unit) where T : Any, T : CommonExtension<*, *, *, *, *, *> {
    check(T::class == ApplicationExtension::class || T::class == LibraryExtension::class) {
        "T must be either ApplicationExtension or LibraryExtension"
    }
    configure<T> {
        block()
    }
}

inline fun Project.java(crossinline block: JavaPluginExtension.() -> Unit) {
    configure<JavaPluginExtension> {
        block()
    }
}

inline fun Project.lint(crossinline block: Lint.() -> Unit) {
    configure<Lint> {
        block()
    }
}

inline fun Project.composeCompiler(crossinline block: ComposeCompilerGradlePluginExtension.() -> Unit) {
    configure<ComposeCompilerGradlePluginExtension> {
        block()
    }
}

inline fun <reified T> Project.androidComponents(crossinline block: T.() -> Unit) where T : Any, T : AndroidComponentsExtension<*, *, *> {
    check(T::class == ApplicationAndroidComponentsExtension::class || T::class == LibraryAndroidComponentsExtension::class) {
        "T must be either ApplicationExtension or LibraryExtension"
    }
    configure<T> {
        block()
    }
}

