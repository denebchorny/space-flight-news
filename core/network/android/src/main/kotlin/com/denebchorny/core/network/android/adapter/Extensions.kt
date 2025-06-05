package com.denebchorny.core.network.android.adapter

import kotlinx.coroutines.delay

/**
 * Checks if the result is an error.
 *
 * @return `true` if the result is a [NetworkResponse.Error], `false` otherwise.
 */
val <S, E> NetworkResponse<S, E>.isError: Boolean
    get() = this is NetworkResponse.Error

/**
 * Checks if the result is a success.
 *
 * @return `true` if the result is a [NetworkResponse.Success], `false` otherwise.
 */
val <S, E> NetworkResponse<S, E>.isSuccess: Boolean
    get() = this is NetworkResponse.Success

/**
 * Transforms this [NetworkResponse] into a value of type [R] by applying the provided [onSuccess] or [onError] function.
 *
 * @param R The type of the result after transformation.
 * @param onSuccess A lambda that handles the [Success] case.
 * @param onError A lambda that handles the [Error] case, receiving the error body and throwable.
 * @return The result of the [onSuccess] or [onError] transformation.
 */
fun <S, E, R> NetworkResponse<S, E>.fold(
    onSuccess: (S) -> R,
    onError: (E?, Throwable?) -> R
): R = when (this) {
    is NetworkResponse.Success -> onSuccess(body)
    is NetworkResponse.Error -> onError(body, error)
}

/**
 * Retries the given [block] for the specified number of times in the case of [NetworkResponse.NetworkError]
 *
 * @param T The success body type of [NetworkResponse]
 * @param U The error body type of [NetworkResponse]
 * @param times The number of times this request should be retried
 * @param initialDelay The initial amount of time to wait before retrying
 * @param maxDelay The max amount of time to wait before retrying
 * @param factor Multiply current delay time with this on each retry
 * @param block The suspending function to be retried
 * @return The NetworkResponse value whether it be successful or failed after retrying
 */
public suspend inline fun <T : Any, U : Any> executeWithRetry(
    times: Int = 10,
    initialDelay: Long = 100, // 0.1 second
    maxDelay: Long = 1000, // 1 second
    factor: Double = 2.0,
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    block: suspend () -> NetworkResponse<T, U>
): NetworkResponse<T, U> {
    var currentDelay = initialDelay
    repeat(times - 1) {
        when (val response = block()) {
            is NetworkResponse.NetworkError -> {
                delay(currentDelay)
                currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
            }
            else -> return response
        }
    }
    return block() // last attempt
}

/**
 * Performs the given action if this is a [NetworkResponse.Success].
 *
 * @param fn The action to perform on the success body.
 * @return The original [NetworkResponse] for chaining.
 */
fun <S, E> NetworkResponse<S, E>.onSuccess(fn: (S) -> Unit): NetworkResponse<S, E> =
    this.apply { if (this is NetworkResponse.Success) fn(body) }

/**
 * Executes the given suspend function if this is a [NetworkResponse.Success].
 *
 * @param fn The suspend function to execute with the success body.
 * @return The original [NetworkResponse] for chaining.
 */
suspend fun <S, E> NetworkResponse<S, E>.onSuccessSuspend(fn: suspend (S) -> Unit): NetworkResponse<S, E> {
    if (this is NetworkResponse.Success) {
        fn(body)
    }
    return this
}


/**
 * Performs the given action if this is a [NetworkResponse.Error].
 *
 * @param fn The action to perform on the error body and throwable.
 * @return The original [NetworkResponse] for chaining.
 */
fun <S, E> NetworkResponse<S, E>.onError(fn: (E?, Throwable?) -> Unit): NetworkResponse<S, E> =
    this.apply { if (this is NetworkResponse.Error) fn(body, error) }

/**
 * Executes the given suspend function if this is a [NetworkResponse.Error].
 *
 * @param fn The suspend function to execute with the error body and throwable.
 * @return The original [NetworkResponse] for chaining.
 */
suspend fun <S, E> NetworkResponse<S, E>.onErrorSuspend(fn: suspend (E?, Throwable?) -> Unit): NetworkResponse<S, E> {
    if (this is NetworkResponse.Error) {
        fn(body, error)
    }
    return this
}

/**
 * Overloaded invoke operator to get the successful body or null in NetworkResponse class
 *
 * @param T the success body type of [NetworkResponse]
 * @param U the error body type of [NetworkResponse]
 *
 * Example:
 * val usersResponse = executeWithRetry { getUsers() }
 *
 * println(usersResponse() ?: "No users found")
 */
public operator fun <T : Any, U : Any> NetworkResponse<T, U>.invoke(): T? {
    return if (this is NetworkResponse.Success) body else null
}
