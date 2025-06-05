package com.denebchorny.core.common.jvm.result

/**
 * Represents a marker interface for errors.
 *
 * This interface is optional and can be used with the [Result] class to group errors of the same type.
 * By implementing this interface, you can standardize error handling and ensure consistency
 * across different parts of your application.
 *
 * For example, you can define specific error types:
 *
 * ```
 * sealed class NetworkError : Error {
 *     object Timeout : NetworkError()
 *     object ConnectionLost : NetworkError()
 * }
 *
 * sealed class OtherError : Error {
 *     object Error1 : NetworkError()
 *     object Error2 : NetworkError()
 * }
 * ```
 *
 * Then use it with the [Result] class:
 *
 * ```
 * val result: Result<Error, String> = ...
 * ```
 */
interface Error