package theme

enum class Appearance {
    Light,
    Dark,
    FollowSystem;

    companion object {
        val default = FollowSystem
        fun find(value: String?) =
            Appearance.entries.find { it.name == value } ?: Appearance.default
    }
}