package com.denebchorny.core.designsystem.theme.appearance

enum class Appearance {
    Light,
    Dark,
    FollowSystem;

    companion object {
        val default = Light
        fun find(value: String?) =
            Appearance.entries.find { it.name == value } ?: default
    }
}