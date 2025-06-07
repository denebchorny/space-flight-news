package com.denebchorny.feature.articles.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Minimal Launch reference as embedded under Article, Blog, or Report.
 *
 * @property id  The UUID string of the launch.
 * @property provider  Which service provided this launch (e.g. “Launch Library 2”).
 */
@Serializable
data class LaunchDTO(
    @SerialName("launch_id")
    val id: String,
    val provider: String?
)
