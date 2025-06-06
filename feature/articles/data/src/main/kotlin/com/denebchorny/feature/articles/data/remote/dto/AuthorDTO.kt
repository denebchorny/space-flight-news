package com.denebchorny.feature.articles.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Represents an author of an Article, Blog, or Report.
 *
 * @property name   The author’s name. Required, max length 250.
 * @property socials The author’s social links. See [SocialsDTO].
 */
@Serializable
data class AuthorDTO(
    val name: String,
    val socials: SocialsDTO?
)