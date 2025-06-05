package com.denebchorny.core.model.info

/**
 * Domain model for the info.
 *
 * @property version     version string.
 * @property newsSites   List of strings naming supported news sites.
 */
data class Info(
    val version: String,
    val newsSites: List<String>
)