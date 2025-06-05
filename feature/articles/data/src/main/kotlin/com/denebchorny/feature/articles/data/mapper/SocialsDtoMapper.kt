package com.denebchorny.feature.articles.data.mapper

import com.denebchorny.core.model.socials.Socials
import com.denebchorny.feature.articles.data.remote.dto.SocialsDTO

fun SocialsDTO.toSocials(): Socials {
    return Socials(
        x = x.orEmpty(),
        youtube = youtube.orEmpty(),
        instagram = instagram.orEmpty(),
        linkedin = linkedin.orEmpty(),
        mastodon = mastodon.orEmpty(),
        bluesky = bluesky.orEmpty()
    )
}

