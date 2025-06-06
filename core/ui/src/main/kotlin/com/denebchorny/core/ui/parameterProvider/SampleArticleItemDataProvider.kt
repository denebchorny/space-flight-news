package com.denebchorny.core.ui.parameterProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.denebchorny.core.ui.component.card.ArticleItemData

class SampleArticleItemDataProvider : PreviewParameterProvider<ArticleItemData> {
    override val values = sequenceOf(
        ArticleItemData(
            id = 1,
            title = "NASA's PACE Mission Reveals a Year of Terrestrial Data on Plant Health",
            imageUrl = "https://www.nasa.gov/wp-content/uploads/2025/06/pace-land-veg-north-america-dates-legend-00370-m.jpg",
            summary = "A lot can change in a year for Earth's forests and vegetation, as springtime and rainy seasons can bring new growth, while cooling temperatures and dry weather can bring a dieback of those green colors. And now, a novel type...",
        ),
        ArticleItemData(
            id = 2,
            title = "NASA Astronaut Jeanette Epps Retires",
            imageUrl = "https://www.nasa.gov/wp-content/uploads/2025/06/53738633187-c3b7903f66-k.jpg",
            summary = "NASA astronaut Jeanette Epps retired May 30, after nearly 16 years of service with the agency. Epps most recently served as a mission specialist during NASA's SpaceX Crew-8 mission, spending 235 days in space, including 232 days aboard the International...",
        ),
        ArticleItemData(
            id = 3,
            title = "Jack Kaye Retires After a Storied Career at NASA",
            imageUrl = "https://via.placeholder.com/150",
            summary = "Jack Kaye [NASA HQ — Associate Director for Research, Earth Science Division (ESD)] has decided to retire on April 30, 2025, following 42 years of service to NASA — see Photo 1. Most recently, Kaye served as associate director for research of...",
        )
    )
}