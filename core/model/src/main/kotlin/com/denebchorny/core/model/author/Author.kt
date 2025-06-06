package com.denebchorny.core.model.author

import com.denebchorny.core.model.socials.Socials

/**
 * Represents an author of an Article, Blog, or Report.
 *
 * @property name   The author’s name. Required, max length 250.
 * @property socials The author’s social links. See [Socials].
 */
data class Author(
    val name: String,
    val socials: Socials?
)