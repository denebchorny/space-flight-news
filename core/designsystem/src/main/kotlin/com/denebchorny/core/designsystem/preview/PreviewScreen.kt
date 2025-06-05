package com.denebchorny.core.designsystem.preview

import androidx.compose.ui.tooling.preview.Preview

const val phone = "spec:width=411dp,height=891dp"
const val foldable = "spec:width=673dp,height=841dp"
const val tablet = "spec:width=1280dp,height=800dp,dpi=240"
const val landScapePhone = "spec:" +
        "width=411dp," +
        "height=891dp," +
        "dpi=420," +
        "isRound=false," +
        "chinSize=0dp," +
        "orientation=landscape"

@Preview(name = "Phone", device = phone)
@Preview(name = "Phone (Landscape)", device = landScapePhone)
@Preview(name = "Foldable (Unfolded)", device = foldable)
@Preview(name = "Tablet", device = tablet)
annotation class PreviewScreen