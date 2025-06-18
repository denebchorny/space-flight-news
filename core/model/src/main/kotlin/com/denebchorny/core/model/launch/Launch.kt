package com.denebchorny.core.model.launch

import kotlinx.serialization.Serializable

/**
 * Minimal Launch reference as embedded under Article, Blog, or Report.
 *
 * @property id  The UUID string of the launch.
 * @property provider  Which service provided this launch (e.g. “Launch Library 2”).
 */
@Serializable
data class Launch(
    val id: String,
    val provider: String
)
