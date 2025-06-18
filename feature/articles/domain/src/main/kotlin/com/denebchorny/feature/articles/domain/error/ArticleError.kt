package com.denebchorny.feature.articles.domain.error

sealed class ArticleError {
    /**
     * Represents errors related to network connectivity (e.g., no internet, DNS issues, timeouts).
     */
    data object NetworkError : ArticleError()

    /**
     * Represents errors reported by the server (e.g., HTTP 4xx or 5xx errors).
     * @param code The HTTP status code, if available.
     * @param message The error message from the server, if available.
     */
    data class ServerError(val code: Int?, val message: String?) : ArticleError()

    /**
     * Represents a situation where data could not be loaded from the local cache
     * and network retrieval also failed or was not possible.
     */
    data object NoCacheError : ArticleError()

    /**
     * Represents errors due to invalid or unexpected data format from the API or cache,
     * such as parsing issues or missing required fields.
     */
    data object InvalidDataError : ArticleError()

    /**
     * Represents any other unexpected or unknown errors that don't fit into other categories.
     */
    data object UnknownError : ArticleError()
}