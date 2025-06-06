package com.denebchorny.feature.articles.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Represents URIs for various social platforms.
 *
 * All fields are required and must not exceed 200 characters in length.
 */
@Serializable
data class SocialsDTO(
    val x: String?,
    val youtube: String?,
    val instagram: String?,
    val linkedin: String?,
    val mastodon: String?,
    val bluesky: String?
)