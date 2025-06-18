package com.denebchorny.core.model.socials

import kotlinx.serialization.Serializable

/**
 * Represents URIs for various social platforms.
 *
 * All fields are required and must not exceed 200 characters in length.
 */
@Serializable
data class Socials(
    val x: String,
    val youtube: String,
    val instagram: String,
    val linkedin: String,
    val mastodon: String,
    val bluesky: String
)