package com.denebchorny.feature.articles.data.remote.dto

/**
 * Represents URIs for various social platforms.
 *
 * All fields are required and must not exceed 200 characters in length.
 */
data class SocialsDTO(
    val x: String?,
    val youtube: String?,
    val instagram: String?,
    val linkedin: String?,
    val mastodon: String?,
    val bluesky: String?
)