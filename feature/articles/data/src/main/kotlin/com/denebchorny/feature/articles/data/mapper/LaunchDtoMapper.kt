package com.denebchorny.feature.articles.data.mapper

import com.denebchorny.core.model.launch.Launch
import com.denebchorny.feature.articles.data.remote.dto.LaunchDTO

fun LaunchDTO.toLaunch(): Launch {
    return Launch(
        id = id,
        provider = provider
    )
}

fun List<LaunchDTO>.toLaunchList(): List<Launch> = this.map { it.toLaunch() }
