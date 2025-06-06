package com.denebchorny.feature.articles.data.mapper

import com.denebchorny.core.model.author.Author
import com.denebchorny.feature.articles.data.remote.dto.AuthorDTO

fun AuthorDTO.toAuthor(): Author {
    return Author(
        name = name,
        socials = socials?.toSocials()
    )
}

fun List<AuthorDTO>.toAuthorList(): List<Author> = this.map { it.toAuthor() }