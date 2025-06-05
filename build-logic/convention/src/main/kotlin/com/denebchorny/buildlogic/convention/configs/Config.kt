package com.denebchorny.buildlogic.convention.configs

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object Config {
    object App {
        const val id: String = "com.denebchorny.spaceflightnews"
        const val name: String = "Space Flight News"
    }

    object JAVA {  val version = JavaVersion.VERSION_17 }
    object JVM { val version = JvmTarget.JVM_17 }

    val sdk = Sdk(compile = 35, min = 29, target = 35)
    val project = SemanticVersioning(1, 0, 0, 0)
}