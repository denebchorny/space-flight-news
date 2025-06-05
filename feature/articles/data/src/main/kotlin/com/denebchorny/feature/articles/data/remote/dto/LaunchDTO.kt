package com.denebchorny.feature.articles.data.remote.dto

/**
 * Minimal Launch reference as embedded under Article, Blog, or Report.
 *
 * @property id  The UUID string of the launch.
 * @property provider  Which service provided this launch (e.g. “Launch Library 2”).
 */
data class LaunchDTO(
    val id: String,
    val provider: String
)
